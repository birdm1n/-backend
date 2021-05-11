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
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.wms.dto.request.InStockInsertReqDto;
import com.daema.rest.wms.dto.request.InStockUpdateReqDto;
import com.daema.rest.wms.dto.request.InStockWaitInsertReqDto;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.*;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final AuthenticationUtil authenticationUtil;

    public InStockMgmtService(InStockRepository inStockRepository, DeviceRepository deviceRepository, PubNotiRepository pubNotiRepository, StockRepository stockRepository, GoodsRepository goodsRepository, GoodsOptionRepository goodsOptionRepository, InStockWaitRepository inStockWaitRepository, DeviceStatusRepository deviceStatusRepository, CodeDetailRepository codeDetailRepository, StoreStockRepository storeStockRepository, AuthenticationUtil authenticationUtil) {
        this.inStockRepository = inStockRepository;
        this.deviceRepository = deviceRepository;
        this.pubNotiRepository = pubNotiRepository;
        this.stockRepository = stockRepository;
        this.goodsRepository = goodsRepository;
        this.goodsOptionRepository = goodsOptionRepository;
        this.inStockWaitRepository = inStockWaitRepository;
        this.deviceStatusRepository = deviceStatusRepository;
        this.codeDetailRepository = codeDetailRepository;
        this.storeStockRepository = storeStockRepository;
        this.authenticationUtil = authenticationUtil;
    }

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
        // 중복 입력 확인용
        InStockWait selectEntity = inStockWaitRepository.findByFullBarcodeAndDelYn(requestDto.getFullBarcode(), StatusEnum.FLAG_N.getStatusMsg());
        if (selectEntity != null) return ResponseCodeEnum.DUPL_DATA;

        //device 테이블에 중복된 기기가 있는지 확인
        Device duplDvc = deviceRepository.findByFullBarcodeAndDelYn(requestDto.getFullBarcode(), StatusEnum.FLAG_N.getStatusMsg()).orElse(null);
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
        if (requestDto.getGoodsOptionId() == null) return ResponseCodeEnum.NO_COLOR;
        if (requestDto.getCapacity() == null) return ResponseCodeEnum.NO_CAPACITY;

        GoodsOption goodsOptionEntity = goodsOptionRepository.findById(requestDto.getGoodsOptionId()).orElse(null);
        Goods goodsEntity = goodsOptionEntity.getGoods();
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
                        .capacity(goodsEntity.getCapacity())
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
                inStockWaitIds.add(reqDto.getWaitId());

                devices.add(
                        Device.builder()
                                .barcodeType(reqDto.getBarcodeType())
                                .fullBarcode(reqDto.getFullBarcode())
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
                                .build()
                );

                inStocks.add(
                        InStock.builder()
                                .inStockStatus(reqDto.getInStockStatus())
                                .statusStr(reqDto.getStatusStr())
                                .inStockAmt(reqDto.getInStockAmt())
                                .inStockMemo(reqDto.getInStockMemo())
                                .provider(
                                        Provider.builder()
                                                .provId(reqDto.getProvId())
                                                .build()
                                )
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
            for (int i = 0; i < inStocks.size(); i++){
                InStock tmpInStock = inStocks.get(i);
                storeStocks.get(i).setStockTypeId(tmpInStock.getInStockId());
            }

            // 4. 재고 insert
            storeStockRepository.saveAll(storeStocks);

            // 5. 입고 대기 목록 삭제
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
    public List<SearchMatchResponseDto> getDeviceList(int telecomId, int makerId) {
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
        inStock.setInStockStatus(requestDto.getInStockStatus());
        inStock.setInStockMemo(requestDto.getInStockMemo());

        DeviceStatus deviceStatus = inStock.getInDeviceStatus();
        deviceStatus.setExtrrStatus(requestDto.getExtrrStatus());
        deviceStatus.setProductFaultyYn(requestDto.getProductFaultyYn());
        deviceStatus.setDdctAmt(requestDto.getDdctAmt());
        deviceStatus.setAddDdctAmt(requestDto.getAddDdctAmt());
        deviceStatus.setProductMissYn(requestDto.getProductMissYn());
        deviceStatus.setMissProduct(requestDto.getMissProduct());


    }
}