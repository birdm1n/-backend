package com.daema.rest.wms.service;

import com.daema.base.domain.Member;
import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.MoveStockAlarmDto;
import com.daema.rest.wms.dto.request.SellMoveInsertReqDto;
import com.daema.rest.wms.dto.request.StockMoveInsertReqDto;
import com.daema.rest.wms.dto.request.StockTransInsertReqDto;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    private final StoreStockHistoryMgmtService storeStockHistoryMgmtService;

    @Transactional(readOnly = true)
    public ResponseDto<MoveStockResponseDto> getMoveAndTrnsList(WmsEnum.MovePathType movePathType) {
        long sotreId = authenticationUtil.getStoreId();
        // 이동 프로세스
        if (WmsEnum.MovePathType.SELL_MOVE == movePathType || WmsEnum.MovePathType.STOCK_MOVE == movePathType) {
            Page<MoveStockResponseDto> resultPageDto = moveStockRepository.getMoveTypeList(movePathType);
        }

        // 이관 프로세스
        if (WmsEnum.MovePathType.SELL_TRNS == movePathType ||
                WmsEnum.MovePathType.STOCK_TRNS == movePathType ||
                WmsEnum.MovePathType.FAULTY_TRNS == movePathType) {
            Page<MoveStockResponseDto> resultPageDto = moveStockRepository.getTransTypeList(movePathType);
        }

        return null;
    }

    @Transactional
    public ResponseCodeEnum insertSellMove(SellMoveInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String fullbarcode = requestDto.getFullBarcode();
        Store store = Store.builder()
                .storeId(storeId)
                .build();
        // 1. [기기] 정보 조회
        Device device = deviceRepository.findByFullBarcodeAndStoreAndDelYn(fullbarcode, store, "N");
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
                .cusPhone(requestDto.getCusPhone())
                .usimFullBarcode(requestDto.getUsimFullBarcode())
                .zipCode(requestDto.getZipCode())
                .addr1(requestDto.getAddr1())
                .addr2(requestDto.getAddr2())
                .courier(requestDto.getCourier())
                .invoiceNo(requestDto.getInvoiceNo())
                .deliveryMemo(requestDto.getDeliveryMemo())
                .deliveryStatus(deliveryStatus)
                .build();
        delivery = deliveryRepository.save(delivery);

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
        moveStock = moveStockRepository.save(moveStock);

        // 5. [재고] update
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.setStockType(WmsEnum.StockType.SELL_MOVE);
        storeStock.setStockTypeId(moveStock.getMoveStockId());


        return ResponseCodeEnum.OK;
    }
    @Transactional
    public ResponseCodeEnum insertStockMove(StockMoveInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String fullbarcode = requestDto.getFullBarcode();
        Store store = Store.builder()
                .storeId(storeId)
                .build();
        // 1. [기기] 정보 조회
        Device device = deviceRepository.findByFullBarcodeAndStoreAndDelYn(fullbarcode, store, "N");
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
        delivery = deliveryRepository.save(delivery);

        
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
        moveStock = moveStockRepository.save(moveStock);

        // 5. [재고] update
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.setStockType(WmsEnum.StockType.STOCK_MOVE);
        storeStock.setStockTypeId(moveStock.getMoveStockId());
        storeStock.setPrevStock(prevStock);
        storeStock.setNextStock(nextStock);
        // 6. [재고이력] insert, update
        storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
        storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);

        return ResponseCodeEnum.OK;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void setLongTimeStoreStockAlarm(MoveStockAlarmDto requestDto){

        Store store = Store.builder().storeId(requestDto.getStoreId()).build();
        Member member = Member.builder().seq(requestDto.getMemberSeq()).build();

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
        String fullbarcode = requestDto.getFullBarcode();
        Store store = Store.builder()
                .storeId(storeId)
                .build();
        // 1. [기기] 정보 조회
        Device device = deviceRepository.findByFullBarcodeAndStoreAndDelYn(fullbarcode, store, "N");
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
        delivery = deliveryRepository.save(delivery);


        // 4. [이관] insert
        OutStock outStock = OutStock
                .builder()
                .outStockType(WmsEnum.OutStockType.STOCK_TRNS)
                .targetId(requestDto.getTransStoreId())
                .device(device)
                .delivery(delivery)
                .store(store)
                .build();

        outStock = outStockRepository.save(outStock);

        // 이전 보유처, 이동할 보유처 정보
        Stock prevStock = storeStock.getNextStock(); //이전 보유처

        // 5. [재고] update
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.setStockType(WmsEnum.StockType.STOCK_TRNS);
        storeStock.setStockTypeId(outStock.getOutStockId());
        storeStock.setPrevStock(prevStock);
        storeStock.setNextStock(null);
        // 6. [재고이력] insert, update
        storeStockHistoryMgmtService.insertStoreStockHistory(storeStock);
        storeStockHistoryMgmtService.arrangeStoreStockHistory(storeStock, false);

        return ResponseCodeEnum.OK;
    }
}