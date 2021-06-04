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
@org.hibernate.annotations.Table(appliesTo = "move_stock", comment = "이동 재고")
public class MoveStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "move_stock_id", columnDefinition = "BIGINT unsigned comment '이동 재고 아이디'")
    private Long moveStockId;

    @Column(name = "move_stock_type", columnDefinition = "varchar(255) comment '이동 재고 타입'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.MoveStockType moveStockType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id", columnDefinition = "BIGINT unsigned comment '기기 아이디'")
    private Device device;

    /**
     * 현재 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_stock_id", referencedColumnName = "stock_id", columnDefinition = "BIGINT unsigned comment '이전 재고 아이디'")
    private Stock prevStock;


    /**
     * 다음 보유처
     * 2 : 사용자에게 넘어 감. (창고 아이디 없음 stock 맵핑 불가)
     * 3 : 다음 보유처(내 보유처 또는 내 하위 보유처)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_stock_id", referencedColumnName = "stock_id", columnDefinition = "BIGINT unsigned comment '다음 재고 아이디'")
    private Stock nextStock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", columnDefinition = "BIGINT unsigned comment '배송 아이디'")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
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
