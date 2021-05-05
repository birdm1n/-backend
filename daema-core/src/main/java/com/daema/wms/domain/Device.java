package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of="dvc_id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="device")
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_id")
    private Long dvcId;
    
    // 바코드 입력 타입
    @Column(name = "barcode_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.BarcodeType barcodeType;

    // 바코드
    @Column(name = "full_barcode")
    private String fullBarcode;
    
    // 불량 여부
    @Nullable
    @Column(name = "product_faulty_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productFaultyYn;

    // 외장상태
    @Enumerated(EnumType.STRING)
    @Column(name = "extrr_status")
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    
    // 누락여부
    @Nullable
    @Column(name = "product_miss_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productMissYn;

    // 누락제품
    @Column(name = "miss_product")
    private String missProduct;

    // 추가 차감비
    @Column(name = "add_ddct_amt")
    private int addDdctAmt;
    
    // 차감비
    @Column(name = "ddct_amt")
    private int ddctAmt;
    
    // 상품옵션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_option_id")
    private GoodsOption goodsOption;

    // 창고
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    // 소유권을 가지는 Store
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "own_store_id", referencedColumnName = "store_id")
    private Store ownStore;

}