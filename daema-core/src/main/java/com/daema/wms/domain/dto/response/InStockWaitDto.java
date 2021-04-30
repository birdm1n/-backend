package com.daema.wms.domain.dto.response;

import com.daema.base.domain.Member;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.Provider;
import com.daema.wms.domain.Stock;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

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

    // 소유권을 가지는 Store = 관리점
    private Long ownStoreId;

    // 보유처
    private Long stockId;
    private String stockName;

    //재고구분 // 1, "매장재고" / 2, "이동재고"
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