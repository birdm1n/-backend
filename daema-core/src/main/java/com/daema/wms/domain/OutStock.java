package com.daema.wms.domain;

import com.daema.base.domain.Member;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="outStockId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="out_stock")
public class OutStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "out_stock_id")
    private Long outStockId;

    @Column
    @Enumerated(EnumType.STRING)
    private WmsEnum.OutStockType outStockType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regi_user_id", referencedColumnName = "seq")
    private Member regiUserId;

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
     * STOCK_TRNS : 다음 보유처(관리점)
     * FAULTY_TRNS : 다음 보유처(공급처)
     * SELL_TRNS : 다음 보유처(관리점 또는 미가입 대리점. 오프라인으로 기기 판매 가능하다고 함)
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
    public OutStock(Long outStockId, WmsEnum.OutStockType outStockType, Member regiUserId, LocalDateTime regiDateTime,
                    Device device, Stock prevStock, Stock nextStock, Delivery delivery, Store store) {
        this.outStockId = outStockId;
        this.outStockType = outStockType;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
        this.device = device;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
        this.delivery = delivery;
        this.store = store;
    }
}
