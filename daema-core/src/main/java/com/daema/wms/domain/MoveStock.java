package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of="moveStockId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="move_stock")
public class MoveStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "move_stock_id")
    private Long moveStockId;

    @Column(name = "move_stock_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.MoveStockType moveStockType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    /**
     * 현재 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_stock_id", referencedColumnName = "stock_id")
    private Stock prevStock;


    /**
     * 다음 보유처
     * 2 : 사용자에게 넘어 감. (창고 아이디 없음 stock 맵핑 불가)
     * 3 : 다음 보유처(내 보유처 또는 내 하위 보유처)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_stock_id", referencedColumnName = "stock_id")
    private Stock nextStock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public MoveStock(Long moveStockId, WmsEnum.MoveStockType moveStockType,
                     Device device, Stock prevStock, Stock nextStock, Delivery delivery, Store store) {
        this.moveStockId = moveStockId;
        this.moveStockType = moveStockType;
        this.device = device;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
        this.delivery = delivery;
        this.store = store;
    }
}
