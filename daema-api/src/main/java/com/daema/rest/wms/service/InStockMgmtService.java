package com.daema.rest.wms.service;

import com.daema.core.base.domain.CodeDetail;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.base.repository.CodeDetailRepository;
import com.daema.core.commgmt.domain.Goods;
import com.daema.core.commgmt.domain.GoodsOption;
import com.daema.core.commgmt.domain.PubNoti;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.commgmt.dto.response.GoodsMatchRespDto;
import com.daema.core.commgmt.repository.GoodsOptionRepository;
import com.daema.core.commgmt.repository.GoodsRepository;
import com.daema.core.commgmt.repository.PubNotiRepository;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.base.service.ShellService;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.io.file.FileUpload;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.core.wms.dto.request.InStockInsertReqDto;
import com.daema.core.wms.dto.request.InStockUpdateReqDto;
import com.daema.core.wms.dto.request.InStockWaitInsertExcelReqDto;
import com.daema.core.wms.dto.request.InStockWaitInsertReqDto;
import com.daema.core.wms.domain.*;
import com.daema.core.wms.dto.request.InStockRequestDto;
import com.daema.core.wms.dto.response.*;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.repository.*;
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

        // ?????? ?????? ?????????
        // goodsId??? ???????????? ????????? ?????? ????????? ??????
        long inStockWaitCnt = inStockWaitRepository.inStockWaitDuplCk(storeId, requestDto.getBarcode(), requestDto.getGoodsId());
        if (inStockWaitCnt > 0L) return ResponseCodeEnum.DUPL_DATA;

        /*
        List<Long> goodsOptionIds = null;
        if(requestDto.getGoodsId() != null) {
            goodsOptionIds = goodsRepository.findById(requestDto.getGoodsId()).orElseGet(null)
                    .getOptionList()
                    .stream().map(GoodsOption::getGoodsOptionId)
                    .collect(Collectors.toList());
        }
        */

        //- device ???????????? ????????? ????????? ????????? ??????
        //  ???????????? ?????? ???????????? optionId ??? ???????????? ????????? ?????? ????????? ??????
        //- optionId ??? ???????????? rawBarocde ?????? ????????? ?????? ??????. 20210622
        long deviceCnt = deviceRepository.deviceDuplCk(store, requestDto.getBarcode(), null);
        if (deviceCnt > 0L) return ResponseCodeEnum.DUPL_DVC;


        // ????????? ??????
        SelectStockDto stockDto = stockRepository.getStock(storeId, requestDto.getTelecom(), requestDto.getStockId());
        if (stockDto == null) return ResponseCodeEnum.NO_STOCK;

        if (storeId == stockDto.getStoreId()) { /* ???????????? */
            stockDto.setStatusStr(WmsEnum.StockStatStr.I);
        } else { /* ???????????? */
            stockDto.setStatusStr(WmsEnum.StockStatStr.M);
        }

        if (requestDto.getGoodsId() == null) {
            return commonBarcodeLogic(requestDto, stockDto, storeId);
        } else {
            return handWritingLogic(requestDto, stockDto, storeId);
        }
    }

    /**
     Desc : ??????????????? ?????? ???????????? insert ??????
     */
    private ResponseCodeEnum handWritingLogic(InStockWaitInsertReqDto requestDto, SelectStockDto stockDto, long storeId) {
        if (requestDto.getColorName() == null) return ResponseCodeEnum.NO_COLOR;
        if (requestDto.getCapacity() == null) return ResponseCodeEnum.NO_CAPACITY;
        //?????? ????????? ????????? ?????? ?????? ??????
        if (requestDto.getUnLockYn() == null) return ResponseCodeEnum.NO_UNLOCK_YN;
        requestDto.setSerialNo(requestDto.getBarcode());

        Goods goodsEntity = goodsRepository.findById(requestDto.getGoodsId()).orElse(null);
        GoodsOption goodsOptionEntity = goodsOptionRepository.findTopByGoodsAndCapacityAndColorNameAndUnLockYnAndDelYn(goodsEntity, requestDto.getCapacity(), requestDto.getColorName(), requestDto.getUnLockYn(), "N");
        if (goodsOptionEntity == null) return ResponseCodeEnum.NO_CAPACITY_COLOR_UNLOCK_YN;

        CodeDetail telecom = codeDetailRepository.findById(goodsEntity.getNetworkAttribute().getTelecom()).orElse(null);
        CodeDetail maker = codeDetailRepository.findById(goodsEntity.getMaker()).orElse(null);

        // ????????? ???????????? ????????? ???????????? ?????? ?????? ????????????.
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
                        .rawBarcode(requestDto.getRawBarcode())  /* ?????? ????????? */
                        .fullBarcode(requestDto.getFullBarcode())       /* ?????? ?????? ????????? */
                        .serialNo(requestDto.getSerialNo())             /* ????????? ?????? */
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
     Desc : ????????????????????? ???????????? insert ??????
     */
    public ResponseCodeEnum commonBarcodeLogic(InStockWaitInsertReqDto requestDto, SelectStockDto stockDto, long storeId) {
        String commonBarcode = requestDto.getBarcode();
        try {
            commonBarcode = CommonUtil.getCmnBarcode(commonBarcode);
        } catch (Exception e) {
            return ResponseCodeEnum.NO_GOODS;
        }
        /* ?????????????????? ?????? fullBarcode ?????? ?????? */
        String rawBarcode = requestDto.getRawBarcode();
        String fullBarcode = rawBarcode.substring(0, rawBarcode.length() - 1); /* ?????? ????????? length -1 */

        // ????????????
        GoodsMatchRespDto goodsMatchRespDto = goodsRepository.goodsMatchBarcode(commonBarcode, requestDto.getTelecom());
        if (goodsMatchRespDto == null){

            //????????? ?????? ?????? ?????? ??????, ??????????????? ?????? ??? ??????
            goodsMatchRespDto = goodsRepository.goodsMatchBarcode(CommonUtil.getUnLockCmnBarcode(requestDto.getBarcode()), requestDto.getTelecom());

            if(goodsMatchRespDto == null) return ResponseCodeEnum.NO_GOODS;
        }
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
                        .rawBarcode(rawBarcode)                         /* ?????? ????????? */
                        .fullBarcode(fullBarcode)                       /* ?????? ?????? ????????? */
                        .serialNo(requestDto.getSerialNo())             /* ????????? ?????? */
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
        //??? ?????? ???????????? 1??? ??????
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
                                .rawBarcode(reqDto.getRawBarcode())     /* ?????? ????????? */
                                .fullBarcode(reqDto.getFullBarcode())   /* ?????? ?????? ????????? */
                                .serialNo(reqDto.getSerialNo())         /* ????????? ?????? */
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

                    Stock nextStock = stockRepository.findById(reqDto.getStockId()).orElse(null); // ????????? ?????????
                    moveStocks.add(
                            // device, delivery ??? insert ??? ??????
                            MoveStock.builder()
                                    .moveStockType(WmsEnum.MoveStockType.STOCK_MOVE)
                                    .prevStock(prevStock)
                                    .nextStock(nextStock)
                                    .store(storeObj)
                                    .build()
                    );
                }
            }
            // 1. ?????? insert
            deviceRepository.saveAll(devices);

            // ?????? ?????? ??????
            int moveIndex = 0; // ??????????????? ?????? ?????????
            for (int i = 0; i < devices.size(); i++) {
                Device tmpDevice = devices.get(i);
                deviceStatuses.get(i).setDevice(tmpDevice);
                inStocks.get(i).setDevice(tmpDevice);
                storeStocks.get(i).setDevice(tmpDevice);

                /* ??????????????? ?????? ?????? ?????? ????????????*/
                if(inStocks.get(i).getStatusStr() == WmsEnum.StockStatStr.M){
                    moveStocks.get(moveIndex).setDevice(tmpDevice);
                    moveIndex++;
                }
            }
            // 2. ???????????? insert
            deviceStatusRepository.saveAll(deviceStatuses);

            // ???????????? ?????? ??????
            for (int i = 0; i < deviceStatuses.size(); i++) {
                DeviceStatus tmpDeviceStatus = deviceStatuses.get(i);
                inStocks.get(i).setInDeviceStatus(tmpDeviceStatus);
            }
            // 3. ?????? insert
            inStockRepository.saveAll(inStocks);
            for (int i = 0; i < inStocks.size(); i++) {
                InStock tmpInStock = inStocks.get(i);
                storeStocks.get(i).setStockTypeId(tmpInStock.getInStockId());
            }

            // 4. ?????? insert
            storeStockRepository.saveAll(storeStocks);

            // 5. ?????? => ?????? ???????????? insert
            for (StoreStock storeStock : storeStocks) {
                storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
            }

            // 6. ?????? ?????? ?????? ??????
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


            // 7. ?????? ????????? ?????? [delivery],[moveStock],[storeStock],[storeStockHistory] insert
            if (CommonUtil.isNotEmptyList(deliveries)) {
                // ???????????? insert
                deliveryRepository.saveAll(deliveries);

                for (int i = 0; i < deliveries.size(); i++) {
                    Delivery tmpDelivery = deliveries.get(i);
                    moveStocks.get(i).setDelivery(tmpDelivery);
                }

                // ???????????? insert
                moveStockRepository.saveAll(moveStocks);


                for (MoveStock moveStock : moveStocks) {
                    // [??????] update
                    StoreStock storeStock = storeStockRepository.findByStoreAndDevice(storeObj, moveStock.getDevice());
                    storeStock.updateToMove(moveStock);

                    // [????????????] insert, update
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
                        //?????? ????????? ??? ???????????? ???????????? ???????????? ?????? ????????????  failBarcode ??? add
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