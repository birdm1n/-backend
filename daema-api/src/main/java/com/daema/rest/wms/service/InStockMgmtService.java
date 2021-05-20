package com.daema.rest.wms.service;

import com.daema.base.domain.CodeDetail;
import com.daema.base.enums.StatusEnum;
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
import com.daema.wms.domain.dto.request.ReturnStockReqDto;
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
    private final FileUpload fileUpload;
    private final AuthenticationUtil authenticationUtil;


    @Transactional(readOnly = true)
    public InStockWaitResponseDto getWaitInStockList(WmsEnum.InStockStatus inStockStatus) {
        long storeId = authenticationUtil.getStoreId();
        InStockWaitResponseDto responseDto = new InStockWaitResponseDto();

        List<InStockWait> entityList = inStockWaitRepository.getList(storeId, inStockStatus);
        List<InStockWaitDto> inStockWaitDtoList = entityList.stream()
                .map(InStockWaitDto::from)
                .collect(Collectors.toList());
        responseDto.setInStockWaitDtoList(inStockWaitDtoList);

        List<InStockWaitGroupDto> inStockWaitGroupDtoList = inStockWaitRepository.groupInStockWaitList(storeId, inStockStatus);
        responseDto.setInStockWaitGroupDtoList(inStockWaitGroupDtoList);

        return responseDto;
    }

    @Transactional
    public ResponseCodeEnum insertWaitInStock(InStockWaitInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        Store store = Store.builder().storeId(storeId).build();
        // 중복 입력 확인용
        InStockWait selectEntity = inStockWaitRepository.findByOwnStoreIdAndFullBarcodeAndDelYn(storeId, requestDto.getFullBarcode(), StatusEnum.FLAG_N.getStatusMsg());
        if (selectEntity != null) return ResponseCodeEnum.DUPL_DATA;

        //device 테이블에 중복된 기기가 있는지 확인
        Device duplDvc = deviceRepository.findByFullBarcodeAndStoreAndDelYn(requestDto.getFullBarcode(), store, StatusEnum.FLAG_N.getStatusMsg());
        if (duplDvc != null) return ResponseCodeEnum.DUPL_DVC;
        // 보유처 정보
        SelectStockDto stockDto = stockRepository.getStock(storeId, requestDto.getTelecom(), requestDto.getStockId());
        if (stockDto == null) return ResponseCodeEnum.NO_STOCK;
        stockDto.setStatusStr(storeId != stockDto.getStoreId() ? WmsEnum.StockStatStr.M : WmsEnum.StockStatStr.I);


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

        Goods goodsEntity = goodsRepository.findById(requestDto.getGoodsId()).orElse(null);
        GoodsOption goodsOptionEntity = goodsOptionRepository.findTopByGoodsAndCapacityAndColorNameAndDelYn(goodsEntity, requestDto.getCapacity(), requestDto.getColorName(), "N");
        if (goodsOptionEntity == null) return ResponseCodeEnum.NO_CAPACITY_COLOR;

        CodeDetail telecom = codeDetailRepository.findById(goodsEntity.getNetworkAttribute().getTelecom()).orElse(null);
        CodeDetail maker = codeDetailRepository.findById(goodsEntity.getMaker()).orElse(null);

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
                        .fullBarcode(requestDto.getFullBarcode())
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
        String commonBarcode = requestDto.getFullBarcode();
        try {
            commonBarcode = CommonUtil.getCmnBarcode(commonBarcode);
        } catch (Exception e) {
            return ResponseCodeEnum.NO_GOODS;
        }

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
                        .fullBarcode(requestDto.getFullBarcode())
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
        List<Long> inStockWaitIds = new ArrayList<>();
        List<Device> devices = new ArrayList<>();
        List<StoreStock> storeStocks = new ArrayList<>();
        List<DeviceStatus> deviceStatuses = new ArrayList<>();
        List<InStock> inStocks = new ArrayList<>();

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
                                .fullBarcode(reqDto.getFullBarcode())
                                .inStockAmt(reqDto.getInStockAmt())
                                .goodsOption(
                                        GoodsOption.builder()
                                                .goodsOptionId(reqDto.getGoodsOptionId())
                                                .build()
                                )
                                .store(storeObj)
                                .build()
                );
                // device 추가
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
                                .stockYn("Y")
                                .nextStock(stockObj)
                                .build()
                );

            }
            // 1. 기기 insert
            devices = deviceRepository.saveAll(devices);

            // 기기 정보 세팅
            for (int i = 0; i < devices.size(); i++) {
                Device tmpDevice = devices.get(i);
                deviceStatuses.get(i).setDevice(tmpDevice);
                inStocks.get(i).setDevice(tmpDevice);
                storeStocks.get(i).setDevice(tmpDevice);
            }
            // 2. 기기상태 insert
            deviceStatuses = deviceStatusRepository.saveAll(deviceStatuses);

            // 기기상태 정보 세팅
            for (int i = 0; i < deviceStatuses.size(); i++) {
                DeviceStatus tmpDeviceStatus = deviceStatuses.get(i);
                inStocks.get(i).setInDeviceStatus(tmpDeviceStatus);
            }
            // 3. 입고 insert
            inStocks = inStockRepository.saveAll(inStocks);
            for (int i = 0; i < inStocks.size(); i++) {
                InStock tmpInStock = inStocks.get(i);
                storeStocks.get(i).setStockTypeId(tmpInStock.getInStockId());
                storeStocks.get(i).setHistoryStatus(WmsEnum.HistoryStatus.USE);
            }

            // 4. 재고 insert
            storeStockRepository.saveAll(storeStocks);

            // 5. 재고 히스토리 insert
            for (StoreStock storeStock : storeStocks) {
                storeStockHistoryRepository.save(storeStock.toHistoryEntity(storeStock));
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
    public List<SearchMatchResponseDto> getDeviceList(Integer telecomId, Integer makerId) {
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
                                .fullBarcode(excelBarcode)
                                .provId(requestDto.getProvId())
                                .telecom(requestDto.getTelecom())
                                .stockId(requestDto.getStockId())
                                .barcodeType(WmsEnum.BarcodeType.S)
                                .inStockStatus(requestDto.getInStockStatus())
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