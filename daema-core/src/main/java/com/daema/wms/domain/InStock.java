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
    @Column(name = "in_stock_id", columnDefinition = "BIGINT unsigned comment '입고 아이디'")
    private Long inStockId;

    @Column(name = "status_str", columnDefinition = "varchar(255) comment '상태 문자열'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockStatStr statusStr;

    @Column(name = "in_stock_memo", columnDefinition = "varchar(255) comment '입고 메모'")
    private String inStockMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prov_id", columnDefinition = "BIGINT unsigned comment '공급 아이디'")
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id", columnDefinition = "BIGINT unsigned comment '기기 아이디'")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_status_id", columnDefinition = "BIGINT unsigned comment '입고된 기기 상태'")
    private DeviceStatus inDeviceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", columnDefinition = "BIGINT unsigned comment '재고'")
    private Stock stock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점'")
    private Store store;
}
