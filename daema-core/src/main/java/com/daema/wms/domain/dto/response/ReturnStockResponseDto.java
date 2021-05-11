package com.daema.wms.domain.dto.response;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnStockResponseDto {

    private Long returnStockId;

    private Long dvcId;

    // 차감비
    private int ddctAmt;

    // 추가 차감비
    private int addDdctAmt;

    // 출고가 차감 YN
    private String ddctReleaseAmtYn;

    // 누락제품
    private String missProduct;

    // 입고메모
    private String returnStockMemo;

    // 소유권을 가지는 Store = 관리점
//    private Long ownStoreId;
//
//    // 보유처 StoreId
//    private Long holdStoreId;

    /**
     Desc : 기기별 입력정보 및 모델별 입력정보
     */
    // 통신사
    private int telecom;
    private String telecomName;

    // 공급처
    private Long provId;

    // 이전 보유처
    private Long prevStockId;
    private String prevStockName;

    // 현재 보유처
    private Long nextStockId;
    private String nextStockName;

    // 재고구분
    private String statusStr;

    //제조사
    private int maker;
    private String makerName;


    // 상품명_모델명
    private long goodsId;
    private String goodsName;
    private String modelName;

    // 용량
    private String capacity;

    // 색상_기기일련번호(바코드)
    private long goodsOptionId;
    private String colorName;
    private String commonBarcode;
    private String fullBarcode;

    // 입고단가
    private int inStockAmt;

    private int returnStockAmt;


    // 입고상태 =  1, "정상"/ 2, "개봉"
    private WmsEnum.InStockStatus returnStockStatus;
    private String returnStockStatusMsg;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 구성품 누락여부 = Y / N
    private String productMissYn;

    // 외장상태 = 1, "상" / 2, "중" / 3, "하" / 4, "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    private String extrrStatusMsg;

    private LocalDateTime regiDateTime;
    private Long regiUserId;
    private String regiUserName;
    private LocalDateTime inStockRegiDateTime;

    private Long diffReturnStockRegiDate;
    private Long diffInStockRegiDate;































}