package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of="outStockId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="out_stock")
public class OutStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "out_stock_id")
    private Long outStockId;

    @Column
    @Enumerated(EnumType.STRING)
    private WmsEnum.OutStockType outStockType;

    /**
     * 다음 보유처(이관처)
     * STOCK_TRNS : 다음 보유처(관리점(store))
     * FAULTY_TRNS : 다음 보유처(공급처(provider))
     * SELL_TRNS : 다음 보유처(관리점(store) 또는 미가입 대리점(미정). 오프라인으로 기기 판매 가능하다고 함)
     */
    @Column(name = "target_id")
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public OutStock(Long outStockId, WmsEnum.OutStockType outStockType, Long targetId,
                    Device device, Delivery delivery, Store store) {
        this.outStockId = outStockId;
        this.outStockType = outStockType;
        this.device = device;
        this.targetId = targetId;
        this.delivery = delivery;
        this.store = store;
    }
}
