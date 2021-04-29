package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    @Column(name = "ddct,_amt")
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

}