package com.daema.core.wms.domain;

import com.daema.core.base.domain.common.BaseEntity;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of="inStockId")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "in_stock", comment = "입고")
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
    @JoinColumn(name = "dvc_status_id", columnDefinition = "BIGINT unsigned comment '기기 상태 아이디'")
    private DeviceStatus inDeviceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", columnDefinition = "BIGINT unsigned comment '재고 아이디'")
    private Stock stock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private Store store;
}
