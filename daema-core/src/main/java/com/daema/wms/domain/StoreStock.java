package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of="store_stock_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="store_stock")
public class StoreStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_stock_id")
    private Long storeStockId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    @Column(name = "stock_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockType stockType;

    /**
     * 재고여부. Y-보유 재고, N-소멸 재고
     */
    @Column(nullable = false, name = "stock_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"Y\"")
    private String stockYn = "Y";

    /**
     * WmsEnum.StockType 에 따라 테이블 join 한다
     */
    @Column(name = "stock_type_id")
    private Long stockTypeId;

    /**
     * 이전 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_stock_id", referencedColumnName = "stock_id")
    private Stock prevStock;

    /**
     * 현재 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_stock_id", referencedColumnName = "stock_id")
    private Stock nextStock;

    @Builder
    public StoreStock(Long storeStockId, Store store, WmsEnum.StockType stockType, Device device,
                      Stock prevStock, Stock nextStock){
        this.storeStockId = storeStockId;
        this.store = store;
        this.stockType = stockType;
        this.device = device;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
    }
}
