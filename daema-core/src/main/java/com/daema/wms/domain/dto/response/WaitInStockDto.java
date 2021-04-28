package com.daema.wms.domain.dto.response;

import com.daema.base.domain.Member;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.Provider;
import com.daema.wms.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@AllArgsConstructor
public class WaitInStockDto {
    private Long waitId;
    private Long dvcId;


    private LocalDateTime regiDateTime;
    private Member regiUserId;
    private LocalDateTime updDateTime;
    private Member updUserId;
    private String delYn;
    private String inStockType;

    private int inStockAmt;
    private String inStockMemo;
    private String productFaultyYn;
    private String extrrStatus;
    private String productMissYn;
    private String missProduct;
    private int ddctAmt;
    private Store ownStore;
    private Store holdStore;
    private GoodsOption goodsOption;
    private Goods goods;
    private Provider provider;

    // 공급처
    private Long provId;

    // 통신사
    private int telecom;
    private String telecomName;

    // 소유권을 가지는 Store = 관리점
    private Long ownStoreId;

    // 보유처
    private Long holdStoreId;
    private String stockName;

    //재고구분



    // 1, "매장재고" / 2, "이동재고" / 3, "판매이동" / 4, "재고이관" / 5, "불량이관"
    private String inStockStatus;
}
