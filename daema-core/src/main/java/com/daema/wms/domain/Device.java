package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of="dvc_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="device")
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_id")
    private Long dvcId;

    @Column(name = "barcode_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.BarcodeType barcodeType;

    @Column(name = "full_barcode")
    private String fullBarcode;

    @Nullable
    @Column(name = "product_faulty_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productFaultyYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "extrr_status")
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @Nullable
    @Column(name = "product_miss_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productMissYn;

    @Column(name = "miss_product")
    private String missProduct;

    @Column(name = "ddct_amt")
    private int ddctAmt;

    // 소유권을 가지는 Store
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_option_id")
    private GoodsOption goodsOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "add_ddct_amt")
    private int addDdctAmt;

}