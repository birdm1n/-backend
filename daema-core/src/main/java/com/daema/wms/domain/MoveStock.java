package com.daema.wms.domain;

import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="move_stock_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="move_stock")
public class MoveStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "move_stock_id")
    private Long moveStockId;

    @Column(name = "move_stock_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.MoveStockType moveStockType;

    @Column(name = "regi_user_id")
    private long regiUserId;

    @CreatedDate
    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

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
    public MoveStock(Long moveStockId, WmsEnum.MoveStockType moveStockType, long regiUserId, LocalDateTime regiDateTime,
                     Device device, Stock prevStock, Stock nextStock, Delivery delivery, Store store) {
        this.moveStockId = moveStockId;
        this.moveStockType = moveStockType;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
        this.device = device;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
        this.delivery = delivery;
        this.store = store;
    }
}
