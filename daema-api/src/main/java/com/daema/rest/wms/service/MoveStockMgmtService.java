package com.daema.rest.wms.service;

import com.daema.base.domain.Member;
import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.MoveStockAlarmDto;
import com.daema.rest.wms.dto.request.SellMoveInsertReqDto;
import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MoveStockMgmtService {

    private final MoveStockRepository moveStockRepository;
    private final MoveStockAlarmRepository moveStockAlarmRepository;
    private final AuthenticationUtil authenticationUtil;
    private final DeliveryRepository deliveryRepository;
    private final StoreStockRepository storeStockRepository;
    private final DeviceRepository deviceRepository;


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
        // 1. 기기 정보 조회
        Device device = deviceRepository.findByFullBarcodeAndStoreAndDelYn(fullbarcode, store, "N");
        if (device == null) return ResponseCodeEnum.NO_DEVICE;

        // 2. 기기 ID, store Id로 재고 조회
        StoreStock storeStock = storeStockRepository.findByStoreAndDevice(store, device);
        if (storeStock == null) return ResponseCodeEnum.NO_STORE_STOCK;

        // 3. 배송 정보 저장
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
                .build();
        delivery = deliveryRepository.save(delivery);

        // 4. 이동현황은 똑같다.
        // 이전 보유처, 이후 보유처 정보 = 판매이동인 경우 동일하다
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

        // 5.
        // - 재고 테이블에 moveId
        // - 재고 상태, 창고 ID(moveStock ID) 변경 => 판매이동 ( 소유권 변하는지 체크 )
        storeStock.setStockType(WmsEnum.StockType.SELL_MOVE);
        storeStock.setStockTypeId(moveStock.getMoveStockId());

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
}