package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(of="wait_id")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="in_stock_wait")
public class InStockWait extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wait_id")
    private Long waitId;

    @NotNull
    @Column(name = "telecom")
    private int telecom;

    @Column(name = "telecom_name")
    private String telecomName;

    @Column(name = "prov_id")
    private Long provId;

    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "stock_name")
    private String stockName;
    // 재고구분
    @Column(name = "status_str")
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockStatStr statusStr;

    @Column(name = "maker")
    private int maker;

    @Column(name = "maker_name")
    private String makerName;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "goods_option_id")
    private Long goodsOptionId;

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "barcode_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.BarcodeType barcodeType;

    @Column(name = "full_barcode")
    private String fullBarcode;

    @Column(name = "common_barcode")
    private String commonBarcode;

    // 입고 단가
    @Column(name = "in_stock_amt")
    private int inStockAmt;
    
    // 입고상태
    @Column(name = "in_stock_status")
    @Enumerated(EnumType.STRING)
    private WmsEnum.InStockStatus inStockStatus;

    // 제품상태
    @Nullable
    @Column(name = "product_faulty_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productFaultyYn = "N";

    // 외장상태
    @Column(name = "extrr_status")
    @Enumerated(EnumType.STRING)
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @Column(name = "in_stock_memo")
    private String inStockMemo;

    @Nullable
    @Column(name = "product_miss_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productMissYn = "N";

    @Column(name = "miss_product")
    private String missProduct;

    @Column(name = "ddct_amt")
    private int ddctAmt;

    @Column(name = "add_ddct_amt")
    private int addDdctAmt;

    @Column(name = "own_store_id")
    private Long ownStoreId;

    @Column(name = "hold_store_id")
    private Long holdStoreId;

    public void updateDelYn(String delYn){
        super.setDelYn(delYn);
    }
}