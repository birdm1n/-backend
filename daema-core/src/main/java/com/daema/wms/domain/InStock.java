package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of="in_stock_id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="in_stock")
public class InStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_stock_id")
    private Long inStockId;

    @Column
    @Enumerated(EnumType.STRING)
    private WmsEnum.InStockStatus inStockStatus;

    @Column(name = "in_stock_amt")
    private int inStockAmt;

    @Column(name = "in_stock_memo")
    private String inStockMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prov_id")
    private Provider provider;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_stock_id")
    private DeviceStock deviceStock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_status_id")
    private DeviceStatus inDeviceStatus;
}
