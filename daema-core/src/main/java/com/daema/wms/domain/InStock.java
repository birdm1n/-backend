package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Store;
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

    @Column(name = "status_str")
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockStatStr statusStr;

    @Column(name = "in_stock_memo")
    private String inStockMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prov_id")
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_status_id")
    private DeviceStatus inDeviceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}
