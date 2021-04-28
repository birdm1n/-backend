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

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class WaitInStockDto {
    private Long waitId;
    private LocalDateTime regiDateTime;
    private Member regiUserId;
    private LocalDateTime updDateTime;
    private Member updUserId;
    private String delYn;
    private String inStockType;
    private String inStockStatus;
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

    //재고구분
    private Long dvcId;

    // 보유처
    private long stockId;
    private String stockName;
}
