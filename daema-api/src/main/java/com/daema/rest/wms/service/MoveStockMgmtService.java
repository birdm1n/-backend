package com.daema.rest.wms.service;

import com.daema.base.domain.Members;
import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.wms.dto.MoveStockAlarmDto;
import com.daema.rest.wms.dto.request.*;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.request.MoveMgmtRequestDto;
import com.daema.wms.domain.dto.request.MoveStockRequestDto;
import com.daema.wms.domain.dto.response.MoveMgmtResponseDto;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.dto.response.TransResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MoveStockMgmtService {

    private final MoveStockRepository moveStockRepository;
    private final MoveStockAlarmRepository moveStockAlarmRepository;
    private final AuthenticationUtil authenticationUtil;
    private final DeliveryRepository deliveryRepository;
    private final OutStockRepository outStockRepository;
    private final DeviceRepository deviceRepository;
    private final StockRepository stockRepository;
    private final StoreStockRepository storeStockRepository;
    private final InStockRepository inStockRepository;
    private final StoreStockHistoryRepository storeStockHistoryRepository;
    private final StoreStockHistoryMgmtService storeStockHistoryMgmtService;

    @Transactional(readOnly = true)
    public ResponseDto<?> getMoveAndTrnsList(WmsEnum.MovePathType movePathType, MoveStockRequestDto moveStockRequestDto) {
        long storeId = authenticationUtil.getStoreId();
        moveStockRequestDto.setStoreId(storeId);
        // 이동 프로세스
        if (WmsEnum.MovePathType.SELL_MOVE == movePathType || WmsEnum.MovePathType.STOCK_MOVE == movePathType) {
            Page<MoveStockResponseDto> responseDtoPage = moveStockRepository.getMoveTypeList(movePathType, moveStockRequestDto);
            return new ResponseDto(responseDtoPage);
        }

        // 이관 프로세스
        if (WmsEnum.MovePathType.SELL_TRNS == movePathType ||
                WmsEnum.MovePathType.STOCK_TRNS == movePathType) {
            Page<TransResponseDto> responseDtoPage = moveStockRepository.getTransTypeList(movePathType, moveStockRequestDto);
            return new ResponseDto(responseDtoPage);
        }
        // 불량이관 프로세스
        if(WmsEnum.MovePathType.FAULTY_TRNS == movePathType){
            Page<TransResponseDto> responseDtoPage = moveStockRepository.getFaultyTransTypeList(movePathType, moveStockRequestDto);
            return new ResponseDto(responseDtoPage);
        }

        return new ResponseDto(null);
    }

    @Transactional
    public ResponseCodeEnum insertSellMove(SellMoveInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String barcode = requestDto.getBarcode();
        Store store = Store.builder()
                .storeId(storeId)
                .build();
        // 1. [기기] 정보 조회
        Device device = deviceRepository.getDeviceWithBarcode(barcode, store, "N");
        if (device == null) return ResponseCodeEnum.NO_DEVICE;

        // 2. [재고] 조회
        StoreStock storeStock = device.getStoreStock();
        if (storeStock == null) return ResponseCodeEnum.NO_STORE_STOCK;


        // 택배 타입인 경우 = 배송중
        WmsEnum.DeliveryStatus deliveryStatus =
                requestDto.getDeliveryType() == WmsEnum.DeliveryType.PS ? WmsEnum.DeliveryStatus.PROGRESS : WmsEnum.DeliveryStatus.NONE;
        // 3. [배송] 정보 저장
        Delivery delivery = Delivery.builder()
                .deliveryType(requestDto.getDeliveryType())
                .cusName(requestDto.getCusName())
                .cusPhone(
                        requestDto.getCusPhone1()
                        .concat(requestDto.getCusPhone2())
                        .concat(requestDto.getCusPhone3())
                )
                .cusPhone1(requestDto.getCusPhone1())
                .cusPhone2(requestDto.getCusPhone2())
                .cusPhone3(requestDto.getCusPhone3())
                .usimFullBarcode(requestDto.getUsimFullBarcode())
                .zipCode(requestDto.getZipCode())
                .addr1(requestDto.getAddr1())
                .addr2(requestDto.getAddr2())
                .courier(requestDto.getCourier())
                .invoiceNo(requestDto.getInvoiceNo())
                .deliveryMemo(requestDto.getDeliveryMemo())
                .deliveryStatus(deliveryStatus)
                .build();
        deliveryRepository.save(delivery);

        // 4. [이동재고] insert
        // 판매이동 이기에 재고 보유처 같음
        MoveStock moveStock = MoveStock
                .builder()
                .moveStockType(WmsEnum.MoveStockType.SELL_MOVE)
                .device(device)
                .prevStock(storeStock.getNextStock())
                .nextStock(storeStock.getNextStock())
                .delivery(delivery)
                .store(store)
                .build();
        moveStockRepository.save(moveStock);

        // 5. [재고] update
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.updateToMove(moveStock);

        storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
        storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);
        return ResponseCodeEnum.OK;
    }
    
    @Transactional  /* 이동 재고 */
    public ResponseCodeEnum insertStockMove(StockMoveInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String barcode = requestDto.getBarcode();
        Store store = Store.builder()
                .storeId(storeId)
                .build();
        // 1. [기기] 정보 조회
        Device device = deviceRepository.getDeviceWithBarcode(barcode, store, "N");
        if (device == null) return ResponseCodeEnum.NO_DEVICE;

        // 2. [재고 ]조회
        StoreStock storeStock = device.getStoreStock();
        if (storeStock == null) return ResponseCodeEnum.NO_STORE_STOCK;
        WmsEnum.StockType stockType = storeStock.getStockType();
        /* 입고상태이거나, 이동재고인 경우에만 이동가능 */
        if(!(stockType == WmsEnum.StockType.IN_STOCK
                || stockType == WmsEnum.StockType.STOCK_MOVE)) {
            return ResponseCodeEnum.NOT_CHNG_STOCK_TYPE;
        }

        // 택배 타입인 경우 = 배송중
        WmsEnum.DeliveryStatus deliveryStatus =
                requestDto.getDeliveryType() == WmsEnum.DeliveryType.PS ? WmsEnum.DeliveryStatus.PROGRESS : WmsEnum.DeliveryStatus.NONE;

        // 3. [배송] 정보 저장
        Delivery delivery = Delivery.builder()
                .deliveryType(requestDto.getDeliveryType())
                .courier(requestDto.getCourier())
                .invoiceNo(requestDto.getInvoiceNo())
                .deliveryMemo(requestDto.getDeliveryMemo())
                .deliveryStatus(deliveryStatus)
                .build();
        deliveryRepository.save(delivery);

        
        // 이전 보유처, 이동할 보유처 정보
        Stock prevStock = storeStock.getNextStock(); //이전 보유처
        Stock nextStock =  stockRepository.findById(requestDto.getNextStockId()).orElse(null); // 이동할 보유처
        
        // 4. [이동재고] insert
        MoveStock moveStock = MoveStock
                .builder()
                .moveStockType(WmsEnum.MoveStockType.STOCK_MOVE)
                .device(device)
                .prevStock(prevStock)
                .nextStock(nextStock)
                .delivery(delivery)
                .store(store)
                .build();
        moveStockRepository.save(moveStock);

        // 5. [재고] update
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.updateToMove(moveStock);

        // 6. [재고이력] insert, update
        storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
        storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);

        return ResponseCodeEnum.OK;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void setLongTimeStoreStockAlarm(MoveStockAlarmDto requestDto){

        Store store = Store.builder().storeId(requestDto.getStoreId()).build();
        Members member = Members.builder().seq(requestDto.getMemberSeq()).build();

        MoveStockAlarm moveStockAlarm = moveStockAlarmRepository.findByStore(store);

        if(moveStockAlarm != null){
            moveStockAlarm.updateMoveStockAlarm(
                    moveStockAlarm
                    , requestDto.getResellDay()
                    , requestDto.getUndeliveredDay()
                    , member
            );
        }else {
            moveStockAlarmRepository.save(
                    MoveStockAlarm.builder()
                            .store(store)
                            .resellDay(requestDto.getResellDay())
                            .undeliveredDay(requestDto.getUndeliveredDay())
                            .updUserId(member)
                            .updDateTime(LocalDateTime.now())
                            .build()
            );
        }
    }

    @Transactional(readOnly = true)
    public List<SearchMatchResponseDto> getTransStoreList() {
        long storeId = authenticationUtil.getStoreId();
        List<Store> transStoreList = moveStockRepository.getTransStoreList(storeId);

        return transStoreList.stream()
                .map(store -> SearchMatchResponseDto.builder()
                        .storeId(store.getStoreId())
                        .storeName(store.getStoreName())
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional
    public ResponseCodeEnum insertStockTrans(StockTransInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String barcode = requestDto.getBarcode();
        Store store = Store.builder()
                .storeId(storeId)
                .build();
        // 1. [기기] 정보 조회
        Device device = deviceRepository.getDeviceWithBarcode(barcode, store, "N");
        if (device == null) return ResponseCodeEnum.NO_DEVICE;

        // 2. [재고 ]조회
        StoreStock storeStock = device.getStoreStock();
        if (storeStock == null) return ResponseCodeEnum.NO_STORE_STOCK;

        // 택배 타입인 경우 = 배송중
        WmsEnum.DeliveryStatus deliveryStatus =
                requestDto.getDeliveryType() == WmsEnum.DeliveryType.PS ? WmsEnum.DeliveryStatus.PROGRESS : WmsEnum.DeliveryStatus.NONE;

        // 3. [배송] 정보 저장
        Delivery delivery = Delivery.builder()
                .deliveryType(requestDto.getDeliveryType())
                .courier(requestDto.getCourier())
                .invoiceNo(requestDto.getInvoiceNo())
                .deliveryMemo(requestDto.getDeliveryMemo())
                .deliveryStatus(deliveryStatus)
                .build();
        deliveryRepository.save(delivery);

        // 이전 보유처, 이동할 보유처 정보
        Stock prevStock = storeStock.getNextStock(); //이전 보유처

        // 4. [출고] insert
        OutStock outStock = OutStock
                .builder()
                .outStockType(WmsEnum.OutStockType.STOCK_TRNS)
                .targetId(requestDto.getTransStoreId())
                .device(device)
                .delivery(delivery)
                .prevStock(prevStock)
                .store(store)
                .build();

        outStockRepository.save(outStock);

        // 5. [재고] update
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.updateToTrans(outStock);

        // 6. [재고이력] insert, update
        storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
        storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);

        // 7. 이관시 기기 delYn => 'Y'
        device.deleteDevice();


        return ResponseCodeEnum.OK;
    }

    @Transactional
    public ResponseCodeEnum insertSellTrans(SellTransInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String barcode = requestDto.getBarcode();
        Store store = Store.builder()
                .storeId(storeId)
                .build();
        // 1. [기기] 정보 조회
        Device device = deviceRepository.getDeviceWithBarcode(barcode, store, "N");
        if (device == null) return ResponseCodeEnum.NO_DEVICE;

        // 2. [재고 ]조회
        StoreStock storeStock = device.getStoreStock();
        if (storeStock == null) return ResponseCodeEnum.NO_STORE_STOCK;

        // 택배 타입인 경우 = 배송중
        WmsEnum.DeliveryStatus deliveryStatus =
                requestDto.getDeliveryType() == WmsEnum.DeliveryType.PS ? WmsEnum.DeliveryStatus.PROGRESS : WmsEnum.DeliveryStatus.NONE;

        // 3. [배송] 정보 저장
        Delivery delivery = Delivery.builder()
                .deliveryType(requestDto.getDeliveryType())
                .courier(requestDto.getCourier())
                .invoiceNo(requestDto.getInvoiceNo())
                .deliveryMemo(requestDto.getDeliveryMemo())
                .deliveryStatus(deliveryStatus)
                .build();
        deliveryRepository.save(delivery);

        // 이전 보유처, 이동할 보유처 정보
        Stock prevStock = storeStock.getNextStock(); //이전 보유처

        // 4. [출고] insert
        OutStock outStock = OutStock
                .builder()
                .outStockType(WmsEnum.OutStockType.SELL_TRNS)
                .targetId(requestDto.getTransStoreId())
                .device(device)
                .delivery(delivery)
                .prevStock(prevStock)
                .store(store)
                .build();

        outStockRepository.save(outStock);



        // 5. [재고] update
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.updateToTrans(outStock);

        // 6. [재고이력] insert, update
        storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
        storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);
        // 7. 이관시 기기 delYn => 'Y'
        device.deleteDevice();
        return ResponseCodeEnum.OK;
    }

    @Transactional
    public ResponseCodeEnum insertFaultyTrans(FaultyTransInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String barcode = requestDto.getBarcode();
        Store store = Store.builder()
                .storeId(storeId)
                .build();
        // 1. [기기] 정보 조회
        Device device = deviceRepository.getDeviceWithBarcode(barcode, store, "N");
        if (device == null) return ResponseCodeEnum.NO_DEVICE;


        // 2. [재고 ]조회
        StoreStock storeStock = device.getStoreStock();
        if (storeStock == null) return ResponseCodeEnum.NO_STORE_STOCK;

        // 택배 타입인 경우 = 배송중
        WmsEnum.DeliveryStatus deliveryStatus =
                requestDto.getDeliveryType() == WmsEnum.DeliveryType.PS ? WmsEnum.DeliveryStatus.PROGRESS : WmsEnum.DeliveryStatus.NONE;

        // 3. [배송] 정보 저장
        Delivery delivery = Delivery.builder()
                .deliveryType(requestDto.getDeliveryType())
                .courier(requestDto.getCourier())
                .invoiceNo(requestDto.getInvoiceNo())
                .deliveryMemo(requestDto.getDeliveryMemo())
                .deliveryStatus(deliveryStatus)
                .build();
        deliveryRepository.save(delivery);

        // 이전 보유처, 이동할 보유처 정보
        Stock prevStock = storeStock.getNextStock(); //이전 보유처

        // 4. [출고] insert
        OutStock outStock = OutStock
                .builder()
                .outStockType(WmsEnum.OutStockType.FAULTY_TRNS)
                .targetId(requestDto.getProvId())
                .device(device)
                .delivery(delivery)
                .prevStock(prevStock)
                .store(store)
                .build();

        outStockRepository.save(outStock);

        // 5. [재고] update
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.updateToTrans(outStock);

        // 6. [재고이력] insert, update
        storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
        storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);
        // 7. 이관시 기기 delYn => 'Y'
        device.deleteDevice();
        return ResponseCodeEnum.OK;
    }
    @Transactional
    public void deleteMoveStock(ModelMap reqModelMap) {
        List<Number> delMoveStockIds = (List<Number>) reqModelMap.get("moveStockId");
        if(CommonUtil.isNotEmptyList(delMoveStockIds)){
            List<MoveStock> moveStockList = moveStockRepository.findAllById(
                    delMoveStockIds.stream()
                    .map(Number::longValue)
                    .collect(Collectors.toList())
            );
            if (CommonUtil.isNotEmptyList(moveStockList)){
                Optional.of(moveStockList).orElseGet(Collections::emptyList)
                        .forEach(moveStock -> {
                            StoreStockHistory storeStockHistory = storeStockHistoryRepository.findByStockTypeAndStockTypeId(WmsEnum.StockType.SELL_MOVE, moveStock.getMoveStockId());
                            StoreStock storeStock = StoreStock
                                    .builder()
                                    .storeStockId(storeStockHistory.getStoreStock().getStoreStockId())
                                    .stockType(WmsEnum.StockType.SELL_MOVE)
                                    .stockTypeId(moveStock.getMoveStockId())
                                    .store(moveStock.getStore())
                                    .device(moveStock.getDevice())
                                    .build();
                            moveStock.setDelYn("Y");
                            storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, true);
                        });
            }else {
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }

    }
    @Transactional
    public ResponseCodeEnum updateSellMove(SellMoveUpdateReqDto requestDto) {
        MoveStock moveStock = moveStockRepository.findById(requestDto.getMoveStockId()).orElse(null);
        Delivery delivery = moveStock.getDelivery();
        delivery.setCusName(requestDto.getCusName());
        delivery.setCusPhone(
                requestDto.getCusPhone1()
                .concat(requestDto.getCusPhone2())
                .concat(requestDto.getCusPhone3())
        );
        delivery.setCusPhone1(requestDto.getCusPhone1());
        delivery.setCusPhone2(requestDto.getCusPhone2());
        delivery.setCusPhone3(requestDto.getCusPhone3());
        delivery.setZipCode(requestDto.getZipCode());
        delivery.setAddr1(requestDto.getAddr1());
        delivery.setAddr2(requestDto.getAddr2());
        delivery.setDeliveryMemo(requestDto.getDeliveryMemo());

        return ResponseCodeEnum.OK;
    }

    @Transactional(readOnly = true)
    public ResponseDto<MoveMgmtResponseDto> getMoveMgmtList(MoveMgmtRequestDto requestDto) {
        requestDto.setStoreId(authenticationUtil.getStoreId());

        Page<MoveMgmtResponseDto> responseDtoPage = moveStockRepository.getMoveMgmtList(requestDto);
        return new ResponseDto(responseDtoPage);
    }
}