package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
@Builder
@Getter
@Setter
@EqualsAndHashCode(of="dvc_status_id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="device_status")
public class DeviceStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_status_id")
    private Long dvcStatusId;

    @Nullable
    @Column(name = "product_faulty_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productFaultyYn = "N";

    @Enumerated(EnumType.STRING)
    @Column(name = "extrr_status")
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @Nullable
    @Column(name = "product_miss_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productMissYn = "N";

    @Column(name = "miss_product")
    private String missProduct;

    @Column(name = "ddct_amt")
    private Integer ddctAmt;

    @Column(name = "add_ddct_amt")
    private Integer addDdctAmt;

    @Nullable
    @Column(name = "out_stock_amt_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String outStockAmtYn = "N";

    @Column(name = "ddct_release_amt_yn", columnDefinition ="char(1)")
    private String ddctReleaseAmtYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    @OneToOne(mappedBy = "inDeviceStatus", fetch = FetchType.LAZY)
    private InStock inStock;

    @OneToOne(mappedBy = "returnDeviceStatus", fetch = FetchType.LAZY)
    private ReturnStock returnStock;

}