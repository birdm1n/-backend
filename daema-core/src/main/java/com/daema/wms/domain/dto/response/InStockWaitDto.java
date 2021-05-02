package com.daema.wms.domain.dto.response;

import com.daema.base.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStockWaitDto {
    private Long waitId;

    private LocalDateTime regiDateTime;
    private Member regiUserId;
    private LocalDateTime updDateTime;
    private Member updUserId;

    // 기기 ID
    private Long dvcId;

    // 차감비
    private int ddctAmt;

    // 누락제품
    private String missProduct;

    // 입고메모
    private String inStockMemo;


    /**
     * Desc : 기기별 입력정보 및 모델별 입력정보
     */

    // 공급처
    private Long provId;

    // 통신사
    private int telecom;
    private String telecomName;

    // 보유처
    private Long stockId;
    private String stockName;

    // 소유권을 가지는 Store = 관리점
    private Long ownStoreId;
    // 보유처 StoreId
    private Long holdStoreId;

    // O - openStore, S - store, I - inner (내부 관리)
    private String stockType;


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

    private long pubNotiId;
    private int releaseAmt;


    // 입고상태 =  1, "정상"/ 2, "개봉"
    private String inStockStatus;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 구성품 누락여부 = Y / N
    private String productMissYn;

    // 외장상태 = 1, "상" / 2, "중" / 3, "하" / 4, "파손"
    private String extrrStatus;

}