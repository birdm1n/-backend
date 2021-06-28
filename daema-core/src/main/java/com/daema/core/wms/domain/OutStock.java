package com.daema.core.wms.domain;

import com.daema.core.base.domain.common.BaseEntity;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of="outStockId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "out_stock", comment = "출고 재고")
public class OutStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "out_stock_id", columnDefinition = "BIGINT unsigned comment '출고 재고 아이디'")
    private Long outStockId;

    @Column(columnDefinition = "varchar(255) comment '출고 타입'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.OutStockType outStockType;

    /**
     * 이전 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_stock_id", referencedColumnName = "stock_id", columnDefinition = "BIGINT unsigned comment '이전 재고 아이디'")
    private Stock prevStock;

    /**
     * 다음 보유처(이관처)
     * STOCK_TRNS : 다음 보유처(관리점(store))
     * FAULTY_TRNS : 다음 보유처(공급처(provider))
     * SELL_TRNS : 다음 보유처(관리점(store) 또는 미가입 대리점(미정). 오프라인으로 기기 판매 가능하다고 함)
     */
    @Column(name = "target_id", columnDefinition = "BIGINT unsigned comment '타겟 아이디'")
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id", columnDefinition = "BIGINT unsigned comment '기기 아이디'")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", columnDefinition = "BIGINT unsigned comment '배송 아이디'")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private Store store;

    @Builder
    public OutStock(Long outStockId, WmsEnum.OutStockType outStockType, Stock prevStock, Long targetId, Device device, Delivery delivery, Store store) {
        this.outStockId = outStockId;
        this.outStockType = outStockType;
        this.prevStock = prevStock;
        this.targetId = targetId;
        this.device = device;
        this.delivery = delivery;
        this.store = store;
    }
}
