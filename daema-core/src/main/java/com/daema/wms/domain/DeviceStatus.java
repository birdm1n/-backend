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
@EqualsAndHashCode(of="dvcStatusId")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="device_status")
public class DeviceStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_status_id", columnDefinition = "BIGINT unsigned comment '기기 상태 아이디'")
    private Long dvcStatusId;

    @Nullable
    @Column(name = "product_faulty_yn", columnDefinition ="char(1) comment '제품 불량 여부'")
    @ColumnDefault("\"N\"")
    private String productFaultyYn = "N";

    @Enumerated(EnumType.STRING)
    @Column(name = "extrr_status", columnDefinition = "varchar(255) comment '외장 상태'")
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @Nullable
    @Column(name = "product_miss_yn", columnDefinition ="char(1) comment '제품 누락 여부'")
    @ColumnDefault("\"N\"")
    private String productMissYn = "N";

    @Column(name = "miss_product" , columnDefinition = "varchar(255) comment '누락 제품'")
    private String missProduct = null;

    @Column(name = "ddct_amt", columnDefinition = "int comment '차감 금액'")
    private Integer ddctAmt;

    @Column(name = "add_ddct_amt", columnDefinition = "int comment '추가 차감 금액'")
    private Integer addDdctAmt;

    /**
     * 출고가 차감 YN
     */
    @Nullable
    @Column(name = "ddct_release_amt_yn", columnDefinition ="char(1) comment '출고가 차감 여부'")
    @ColumnDefault("\"N\"")
    private String ddctReleaseAmtYn = "N";

    @Column(name = "in_stock_status", columnDefinition = "varchar(255) comment '입고상태'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.InStockStatus inStockStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id", columnDefinition = "BIGINT unsigned comment '기기 아이디'")
    private Device device;

    @OneToOne(mappedBy = "inDeviceStatus", fetch = FetchType.LAZY)
    private InStock inStock;

    @OneToOne(mappedBy = "returnDeviceStatus", fetch = FetchType.LAZY)
    private ReturnStock returnStock;

    public void updateDelYn(DeviceStatus deviceStatus, String delYn){
        deviceStatus.setDelYn(delYn);
    }

}