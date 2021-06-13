package com.daema.rest.wms.service;

import com.daema.base.domain.CodeDetail;
import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.base.repository.CodeDetailRepository;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.PubNoti;
import com.daema.commgmt.domain.Store;
import com.daema.commgmt.domain.dto.response.GoodsMatchRespDto;
import com.daema.commgmt.repository.GoodsOptionRepository;
import com.daema.commgmt.repository.GoodsRepository;
import com.daema.commgmt.repository.PubNotiRepository;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.base.service.ShellService;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.io.file.FileUpload;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.wms.dto.request.InStockInsertReqDto;
import com.daema.rest.wms.dto.request.InStockUpdateReqDto;
import com.daema.rest.wms.dto.request.InStockWaitInsertExcelReqDto;
import com.daema.rest.wms.dto.request.InStockWaitInsertReqDto;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.*;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InStockMgmtService {

    private final InStockRepository inStockRepository;
    private final DeviceRepository deviceRepository;
    private final PubNotiRepository pubNotiRepository;
    private final StockRepository stockRepository;
    private final GoodsRepository goodsRepository;
    private final GoodsOptionRepository goodsOptionRepository;
    private final InStockWaitRepository inStockWaitRepository;
    private final DeviceStatusRepository deviceStatusRepository;
    private final CodeDetailRepository codeDetailRepository;
    private final StoreStockRepository storeStockRepository;
    private final StoreStockHistoryRepository storeStockHistoryRepository;
    private final DeliveryRepository deliveryRepository;
    private final MoveStockRepository moveStockRepository;
    private final StoreStockHistoryMgmtService storeStockHistoryMgmtService;
    private final ShellService shellService;
    private final FileUpload fileUpload;
    private final AuthenticationUtil authenticationUtil;


    @Transactional(readOnly = true)
    public InStockWaitResponseDto getWaitInStockList(WmsEnum.InStockStatus inStockStatus) {
        long storeId = authenticationUtil.getStoreId();
        long memberId = authenticationUtil.getMemberSeq();
        InStockWaitResponseDto responseDto = new InStockWaitResponseDto();

        List<InStockWait> entityList = inStockWaitRepository.getList(memberId, storeId, inStockStatus);
        List<InStockWaitDto> inStockWaitDtoList = entityList.stream()
                .map(InStockWaitDto::from)
                .collect(Collectors.toList());
        responseDto.setInStockWaitDtoList(inStockWaitDtoList);

        List<InStockWaitGroupDto> inStockWaitGroupDtoList = inStockWaitRepository.groupInStockWaitList(memberId, storeId, inStockStatus);
        responseDto.setInStockWaitGroupDtoList(inStockWaitGroupDtoList);

        return responseDto;
    }

    @Transactional
    public ResponseCodeEnum insertWaitInStock(InStockWaitInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        long memberId = authenticationUtil.getMemberSeq();
        Store store = Store.builder().storeId(storeId).build();

        // 중복 입력 확인용
        // goodsId와 바코드가 겹치면 동일 기기로 판단
        long inStockWaitCnt = inStockWaitRepository.inStockWaitDuplCk(storeId, requestDto.getBarcode(), requestDto.getGoodsId());
        if (inStockWaitCnt > 0L) return ResponseCodeEnum.DUPL_DATA;

        //device 테이블에 중복된 기기가 있는지 확인
        List<Long> goodsOptionIds = null;
        if(requestDto.getGoodsId() != null) {
            goodsOptionIds = goodsRepository.findById(requestDto.getGoodsId()).orElseGet(null)
                    .getOptionList()
                    .stream().map(GoodsOption::getGoodsOptionId)
                    .collect(Collectors.toList());
        }

        //기등록된 기기 정보에서 optionId 와 바코드가 겹치면 동일 기기로 판단
        long deviceCnt = deviceRepository.deviceDuplCk(store, requestDto.getBarcode(), goodsOptionIds);
        if (deviceCnt > 0L) return ResponseCodeEnum.DUPL_DVC;

        // 보유처 정보
        SelectStockDto stockDto = stockRepository.getStock(storeId, requestDto.getTelecom(), requestDto.getStockId());
        if (stockDto == null) return ResponseCodeEnum.NO_STOCK;

        if (storeId == stockDto.getStoreId()) { /* 매장재고 */
            stockDto.setStatusStr(WmsEnum.StockStatStr.I);
        } else { /* 이동재고 */
            stockDto.setStatusStr(WmsEnum.StockStatStr.M);
        }

        if (requestDto.getGoodsId() == null) {
            return commonBarcodeLogic(requestDto, stockDto, storeId);
        } else {
            return handWritingLogic(requestDto, stockDto, storeId);
        }
    }

    /**
     Desc : 수기바코드 입력 입고대기 insert 로직
     */
    private ResponseCodeEnum handWritingLogic(InStockWaitInsertReqDto requestDto, SelectStockDto stockDto, long storeId) {
        if (requestDto.getColorName() == null) return ResponseCodeEnum.NO_COLOR;
        if (requestDto.getCapacity() == null) return ResponseCodeEnum.NO_CAPACITY;
        //수기 입력시 자급제 조건 필터 추가
        if (requestDto.getUnLockYn() == null) return ResponseCodeEnum.NO_UNLOCK_YN;
        requestDto.setSerialNo(requestDto.getBarcode());

        Goods goodsEntity = goodsRepository.findById(requestDto.getGoodsId()).orElse(null);
        GoodsOption goodsOptionEntity = goodsOptionRepository.findTopByGoodsAndCapacityAndColorNameAndUnLockYnAndDelYn(goodsEntity, requestDto.getCapacity(), requestDto.getColorName(), requestDto.getUnLockYn(), "N");
        if (goodsOptionEntity == null) return ResponseCodeEnum.NO_CAPACITY_COLOR_UNLOCK_YN;

        CodeDetail telecom = codeDetailRepository.findById(goodsEntity.getNetworkAttribute().getTelecom()).orElse(null);
        CodeDetail maker = codeDetailRepository.findById(goodsEntity.getMaker()).orElse(null);

        // 상품의 통신사와 선택한 통신사와 일치 하지 않은경우.
        if (requestDto.getTelecom() != telecom.getCodeSeq()) return ResponseCodeEnum.NOT_MATCH_TELECOM;
        int inStockAmt = 0;
        PubNoti pubNoti = pubNotiRepository.findTopByGoodsIdOrderByRegiDateTimeDesc(goodsEntity.getGoodsId());
        if (pubNoti != null) {
            inStockAmt = pubNoti.getReleaseAmt();
        }
        InStockWait insertEntity =
                InStockWait.builder()
                        .telecom(telecom.getCodeSeq())
                        .telecomName(telecom.getCodeNm())
                        .provId(requestDto.getProvId())
                        .stockId(stockDto.getStockId())
                        .stockName(stockDto.getStockName())
                        .statusStr(stockDto.getStatusStr())
                        .maker(maker.getCodeSeq())
                        .makerName(maker.getCodeNm())
                        .goodsId(goodsEntity.getGoodsId())
                        .goodsName(goodsEntity.getGoodsName())
                        .modelName(goodsEntity.getModelName())
                        .capacity(goodsOptionEntity.getCapacity())
                        .goodsOptionId(goodsOptionEntity.getGoodsOptionId())
                        .colorName(goodsOptionEntity.getColorName())
                        .barcodeType(requestDto.getBarcodeType())
                        .rawBarcode(requestDto.getRawBarcode())  /* 원시 바코드 */
                        .fullBarcode(requestDto.getFullBarcode())       /* 원시 수정 바코드 */
                        .serialNo(requestDto.getSerialNo())             /* 시리얼 넘버 */
                        .inStockAmt(inStockAmt)
                        .inStockStatus(requestDto.getInStockStatus())
                        .productFaultyYn(requestDto.getProductFaultyYn())
                        .extrrStatus(requestDto.getExtrrStatus())
                        .inStockMemo(requestDto.getInStockMemo())
                        .productMissYn(requestDto.getProductMissYn())
                        .missProduct(requestDto.getMissProduct())
                        .ddctAmt(requestDto.getDdctAmt())
                        .addDdctAmt(requestDto.getAddDdctAmt())
                        .ddctReleaseAmtYn(requestDto.getDdctReleaseAmtYn())
                        .ownStoreId(storeId)
                        .holdStoreId(stockDto.getStoreId()) //open_store_id
                        .build();
        inStockWaitRepository.save(insertEntity);


        return ResponseCodeEnum.OK;
    }

    /**
     Desc : 공통바코드입력 입고대기 insert 로직
     */
    public ResponseCodeEnum commonBarcodeLogic(InStockWaitInsertReqDto requestDto, SelectStockDto stockDto, long storeId) {
        String commonBarcode = requestDto.getBarcode();
        try {
            commonBarcode = CommonUtil.getCmnBarcode(commonBarcode);
        } catch (Exception e) {
            return ResponseCodeEnum.NO_GOODS;
        }
        /* 공통바코드인 경우 fullBarcode 로직 적용 */
        String rawBarcode = requestDto.getRawBarcode();
        String fullBarcode = rawBarcode.substring(0, rawBarcode.length() - 1); /* 원시 바코드 length -1 */

        // 상품정보
        GoodsMatchRespDto goodsMatchRespDto = goodsRepository.goodsMatchBarcode(commonBarcode);
        if (goodsMatchRespDto == null) return ResponseCodeEnum.NO_GOODS;
        if (requestDto.getTelecom() != goodsMatchRespDto.getTelecom()) return ResponseCodeEnum.NOT_MATCH_TELECOM;


        int inStockAmt = 0;
        PubNoti pubNoti = pubNotiRepository.findTopByGoodsIdOrderByRegiDateTimeDesc(goodsMatchRespDto.getGoodsId());
        if (pubNoti != null) {
            inStockAmt = pubNoti.getReleaseAmt();
        }

        InStockWait insertEntity =
                InStockWait.builder()
                        .telecom(goodsMatchRespDto.getTelecom())
                        .telecomName(goodsMatchRespDto.getTelecomName())
                        .provId(requestDto.getProvId())
                        .stockId(stockDto.getStockId())
                        .stockName(stockDto.getStockName())
                        .statusStr(stockDto.getStatusStr())
                        .maker(goodsMatchRespDto.getMaker())
                        .makerName(goodsMatchRespDto.getMakerName())
                        .goodsId(goodsMatchRespDto.getGoodsId())
                        .goodsName(goodsMatchRespDto.getGoodsName())
                        .modelName(goodsMatchRespDto.getModelName())
                        .capacity(goodsMatchRespDto.getCapacity())
                        .goodsOptionId(goodsMatchRespDto.getGoodsOptionId())
                        .colorName(goodsMatchRespDto.getColorName())
                        .barcodeType(requestDto.getBarcodeType())
                        .rawBarcode(rawBarcode)                         /* 원시 바코드 */
                        .fullBarcode(fullBarcode)                       /* 원시 수정 바코드 */
                        .serialNo(requestDto.getSerialNo())             /* 시리얼 넘버 */
                        .commonBarcode(commonBarcode)
                        .inStockAmt(inStockAmt)
                        .inStockStatus(requestDto.getInStockStatus())
                        .productFaultyYn(requestDto.getProductFaultyYn())
                        .extrrStatus(requestDto.getExtrrStatus())
                        .inStockMemo(requestDto.getInStockMemo())
                        .productMissYn(requestDto.getProductMissYn())
                        .missProduct(requestDto.getMissProduct())
                        .ddctAmt(requestDto.getDdctAmt())
                        .addDdctAmt(requestDto.getAddDdctAmt())
                        .ddctReleaseAmtYn(requestDto.getDdctReleaseAmtYn())
                        .ownStoreId(storeId)
                        .holdStoreId(stockDto.getStoreId()) //open_store_id
                        .build();
        inStockWaitRepository.save(insertEntity);
        return ResponseCodeEnum.OK;
    }

    @Transactional
    public void deleteWaitInStock(ModelMap reqModelMap) {
        List<Number> delWaitIds = (ArrayList<Number>) reqModelMap.get("waitId");
        if (CommonUtil.isNotEmptyList(delWaitIds)) {
            List<InStockWait> inStockWaitList = inStockWaitRepository.findAllById(
                    delWaitIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );
            if (CommonUtil.isNotEmptyList(inStockWaitList)) {
                Optional.of(inStockWaitList)
                        .orElseGet(Collections::emptyList)
                        .forEach(inStockWait ->
                                inStockWait.updateDelYn(StatusEnum.FLAG_Y.getStatusMsg())
                        );
            } else {
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    @Transactional
    public ResponseCodeEnum insertInStock(List<InStockInsertReqDto> reqListDto) {
        long storeId = authenticationUtil.getStoreId();
        Store storeObj = Store.builder()
                .storeId(storeId)
                .build();
        //내 보유 창고에서 1건 추출
        Stock prevStock = stockRepository.findTopByRegiStoreIdAndStockTypeAndDelYn(storeId,
                TypeEnum.STOCK_TYPE_I.getStatusCode(), StatusEnum.FLAG_N.getStatusMsg());

        List<Long> inStockWaitIds = new ArrayList<>();
        List<Device> devices = new ArrayList<>();
        List<StoreStock> storeStocks = new ArrayList<>();
        List<DeviceStatus> deviceStatuses = new ArrayList<>();
        List<InStock> inStocks = new ArrayList<>();
        List<MoveStock> moveStocks = new ArrayList<>();
        List<Delivery> deliveries = new ArrayList<>();

        if (CommonUtil.isNotEmptyList(reqListDto)) {
            for (InStockInsertReqDto reqDto : reqListDto) {
                Stock stockObj = Stock.builder()
                        .stockId(reqDto.getStockId())
                        .build();
                Provider providerObj = Provider.builder()
                        .provId(reqDto.getProvId())
                        .build();
                inStockWaitIds.add(reqDto.getWaitId());
                devices.add(
                        Device.builder()
                                .barcodeType(reqDto.getBarcodeType())
                                .rawBarcode(reqDto.getRawBarcode())     /* 원시 바코드 */
                                .fullBarcode(reqDto.getFullBarcode())   /* 원시 수정 바코드 */
                                .serialNo(reqDto.getSerialNo())         /* 시리얼 넘버 */
                                .inStockAmt(reqDto.getInStockAmt())
                                .oldMatchState(WmsEnum.OldMatchState.NONE)
                                .goodsOption(
                                        GoodsOption.builder()
                                                .goodsOptionId(reqDto.getGoodsOptionId())
                                                .build()
                                )
                                .store(storeObj)
                                .build()
                );

                deviceStatuses.add(
                        DeviceStatus
                                .builder()
                                .productFaultyYn(reqDto.getProductFaultyYn())
                                .extrrStatus(reqDto.getExtrrStatus())
                                .productMissYn(reqDto.getProductMissYn())
                                .missProduct(reqDto.getMissProduct())
                                .ddctAmt(reqDto.getDdctAmt())
                                .addDdctAmt(reqDto.getAddDdctAmt())
                                .ddctReleaseAmtYn(reqDto.getDdctReleaseAmtYn())
                                .inStockStatus(reqDto.getInStockStatus())
                                .build()
                );

                inStocks.add(
                        InStock.builder()
                                .statusStr(reqDto.getStatusStr())
                                .inStockMemo(reqDto.getInStockMemo())
                                .provider(providerObj)
                                .stock(stockObj)
                                .store(storeObj)
                                .build()
                );

                storeStocks.add(
                        StoreStock.builder()
                                .store(storeObj)
                                .stockType(WmsEnum.StockType.IN_STOCK)
                                .stockYn(StatusEnum.FLAG_Y.getStatusMsg())
                                .nextStock(stockObj)
                                .build()
                );
                if (reqDto.getStatusStr() == WmsEnum.StockStatStr.M) {
                    deliveries.add(
                            Delivery.builder()
                                    .deliveryType(WmsEnum.DeliveryType.UNKNOWN)
                                    .courier(null)
                                    .invoiceNo(null)
                                    .deliveryMemo(null)
                                    .deliveryStatus(WmsEnum.DeliveryStatus.NONE)
                                    .build()
                    );

                    Stock nextStock = stockRepository.findById(reqDto.getStockId()).orElse(null); // 이동할 보유처
                    moveStocks.add(
                            // device, delivery 는 insert 후 추가
                            MoveStock.builder()
                                    .moveStockType(WmsEnum.MoveStockType.STOCK_MOVE)
                                    .prevStock(prevStock)
                                    .nextStock(nextStock)
                                    .store(storeObj)
                                    .build()
                    );
                }
            }
            // 1. 기기 insert
            deviceRepository.saveAll(devices);

            // 기기 정보 세팅
            int moveIndex = 0; // 이동재고를 위한 인덱스
            for (int i = 0; i < devices.size(); i++) {
                Device tmpDevice = devices.get(i);
                deviceStatuses.get(i).setDevice(tmpDevice);
                inStocks.get(i).setDevice(tmpDevice);
                storeStocks.get(i).setDevice(tmpDevice);

                /* 이동재고인 경우 로직 처리 확인필요*/
                if(inStocks.get(i).getStatusStr() == WmsEnum.StockStatStr.M){
                    moveStocks.get(moveIndex).setDevice(tmpDevice);
                    moveIndex++;
                }
            }
            // 2. 기기상태 insert
            deviceStatusRepository.saveAll(deviceStatuses);

            // 기기상태 정보 세팅
            for (int i = 0; i < deviceStatuses.size(); i++) {
                DeviceStatus tmpDeviceStatus = deviceStatuses.get(i);
                inStocks.get(i).setInDeviceStatus(tmpDeviceStatus);
            }
            // 3. 입고 insert
            inStockRepository.saveAll(inStocks);
            for (int i = 0; i < inStocks.size(); i++) {
                InStock tmpInStock = inStocks.get(i);
                storeStocks.get(i).setStockTypeId(tmpInStock.getInStockId());
            }

            // 4. 재고 insert
            storeStockRepository.saveAll(storeStocks);

            // 5. 입고 => 재고 히스토리 insert
            for (StoreStock storeStock : storeStocks) {
                storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
            }

            // 6. 입고 대기 목록 삭제
            List<InStockWait> inStockWaitList = inStockWaitRepository.findAllById(inStockWaitIds);
            if (CommonUtil.isNotEmptyList(inStockWaitList)) {
                Optional.of(inStockWaitList)
                        .orElseGet(Collections::emptyList)
                        .forEach(inStockWait ->
                                inStockWait.updateDelYn(StatusEnum.FLAG_Y.getStatusMsg())
                        );
            } else {
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }


            // 7. 이동 재고인 경우 [delivery],[moveStock],[storeStock],[storeStockHistory] insert
            if (CommonUtil.isNotEmptyList(deliveries)) {
                // 배송정보 insert
                deliveryRepository.saveAll(deliveries);

                for (int i = 0; i < deliveries.size(); i++) {
                    Delivery tmpDelivery = deliveries.get(i);
                    moveStocks.get(i).setDelivery(tmpDelivery);
                }

                // 이동재고 insert
                moveStockRepository.saveAll(moveStocks);


                for (MoveStock moveStock : moveStocks) {
                    // [재고] update
                    StoreStock storeStock = storeStockRepository.findByStoreAndDevice(storeObj, moveStock.getDevice());
                    storeStock.updateToMove(moveStock);

                    // [재고이력] insert, update
                    storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
                    storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);
                }
            }

        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }


        return ResponseCodeEnum.OK;

    }

    @Transactional(readOnly = true)
    public ResponseDto<InStockResponseDto> getInStockList(InStockRequestDto requestDto) {
        requestDto.setStoreId(authenticationUtil.getStoreId());

        Page<InStockResponseDto> responseDtoPage = inStockRepository.getInStockList(requestDto);
        return new ResponseDto(responseDtoPage);
    }

    @Transactional(readOnly = true)
    public List<SearchMatchResponseDto> getDeviceList(Long telecomId, Long makerId) {
        long storeId = authenticationUtil.getStoreId();
        List<Goods> deviceList = inStockRepository.getDeviceList(storeId, telecomId, makerId);

        return deviceList.stream()
                .map(goods -> SearchMatchResponseDto
                        .builder()
                        .goodsId(goods.getGoodsId())
                        .modelName(goods.getGoodsName())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateInStock(InStockUpdateReqDto requestDto) {
        InStock inStock = inStockRepository.findById(requestDto.getInStockId())
                .orElseThrow(() ->
                        new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name())
                );
        inStock.setInStockMemo(requestDto.getInStockMemo());

        DeviceStatus deviceStatus = inStock.getInDeviceStatus();
        deviceStatus.setInStockStatus(requestDto.getInStockStatus());
        deviceStatus.setExtrrStatus(requestDto.getExtrrStatus());
        deviceStatus.setProductFaultyYn(requestDto.getProductFaultyYn());
        deviceStatus.setDdctAmt(requestDto.getDdctAmt());
        deviceStatus.setAddDdctAmt(requestDto.getAddDdctAmt());
        deviceStatus.setProductMissYn(requestDto.getProductMissYn());
        deviceStatus.setMissProduct(requestDto.getMissProduct());
    }

    public Set<String> insertInStockWaitExcel(InStockWaitInsertExcelReqDto requestDto, MultipartHttpServletRequest mRequest) {

        Set<String> failBarcode = new HashSet<>();

        try {
            Map<String, Object> excelMap = fileUpload.uploadExcelAndParser(mRequest.getFile("excelFile"), authenticationUtil.getMemberSeq());

            if (excelMap != null) {
                LinkedHashMap<String, String> headerMap = (LinkedHashMap<String, String>) excelMap.get("headers");
                List<HashMap<String, String>> barcodeList = (List<HashMap<String, String>>) excelMap.get("rows");

                if (CommonUtil.isNotEmptyList(barcodeList)) {
                    String key = headerMap.keySet().iterator().next();

                    List<String> excelBarcodeList = barcodeList.stream()
                            .map(data -> data.get(headerMap.get(key)))
                            .collect(Collectors.toList());


                    for (String excelBarcode : excelBarcodeList) {
                        InStockWaitInsertReqDto inStockWaitInsertReqDto = InStockWaitInsertReqDto.builder()
                                .barcode(excelBarcode)
                                .provId(requestDto.getProvId())
                                .telecom(requestDto.getTelecom())
                                .stockId(requestDto.getStockId())
                                .barcodeType(WmsEnum.BarcodeType.S)
                                .inStockStatus(requestDto.getInStockStatus())
                                .ddctReleaseAmtYn("N")
                                .extrrStatus(WmsEnum.DeviceExtrrStatus.T)
                                .productFaultyYn("N")
                                .productMissYn("N")
                                .build();

                        ResponseCodeEnum responseCodeEnum = insertWaitInStock(inStockWaitInsertReqDto);
                        //엑셀 바코드 중 입고대기 데이터에 추가되지 않는 바코드는  failBarcode 에 add
                        if (ResponseCodeEnum.OK != responseCodeEnum) failBarcode.add(excelBarcode);

                    }
                } else {
                    throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
                }
            }
        } catch (Exception e) {
            throw new ProcessErrorException(e.getMessage());
        }

        return failBarcode;
    }
}