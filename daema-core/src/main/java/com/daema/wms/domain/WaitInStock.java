package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(of="wait_in_stock_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="wait_in_stock")
public class WaitInStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wait_id")
    private Long waitId;

    @NotBlank
    @Column(nullable = false, name = "in_stock_type", columnDefinition ="char(1)")
    @ColumnDefault("1")
    private String inStockType;

    @NotBlank
    @Column(nullable = false, name = "in_stock_status", columnDefinition ="char(1)")
    @ColumnDefault("1")
    private String inStockStatus;

    @Column(name = "in_stock_amt")
    private int inStockAmt;

    @Column(name = "in_stock_memo")
    private String inStockMemo;

    @Column(name = "prov_id")
    private Long provId;

    @Column(name = "stock_id")
    private Long stockId;

    @Nullable
    @Column(name = "product_faulty_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productFaultyYn;

    @NotBlank
    @Column(nullable = false, name = "extrr_status", columnDefinition ="char(1)")
    @ColumnDefault("1")
    private String extrrStatus;

    @Nullable
    @Column(name = "product_miss_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productMissYn;

    @Column(name = "miss_product")
    private String missProduct;

    @Column(name = "ddct_amt")
    private int ddctAmt;

    @Column(name = "own_store_id")
    private Long ownStoreId;

    @Column(name = "hold_store_id")
    private Long holdStoreId;

    @Column(name = "goods_option_id")
    private Long goodsOptionId;

    @Column(name = "goods_id")
    private Long goodsId;

//    @Column(name = "dvc_name")
//    private Long deviceName;


}