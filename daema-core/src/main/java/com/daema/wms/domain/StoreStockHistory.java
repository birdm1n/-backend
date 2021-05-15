package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "storeStockHistoryId")
@ToString
@NoArgsConstructor
@Entity
@Table(name = "store_stock_history")
public class StoreStockHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_stock_history_id")
    private Long storeStockHistoryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_stock_id")
    private StoreStock storeStock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    @Column(name = "stock_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockType stockType;

    @Column(name = "stock_type_id")
    private Long stockTypeId;

    @Column(name = "history_status")
    @Enumerated(EnumType.STRING)
    private WmsEnum.HistoryStatus historyStatus;

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
    public StoreStockHistory(Long storeStockHistoryId, StoreStock storeStock, Store store, WmsEnum.StockType stockType, Device device,
                             Long stockTypeId, Stock prevStock, Stock nextStock, WmsEnum.HistoryStatus historyStatus) {
        this.storeStockHistoryId = storeStockHistoryId;
        this.storeStock = storeStock;
        this.store = store;
        this.device = device;
        this.stockType = stockType;
        this.stockTypeId = stockTypeId;
        this.historyStatus = historyStatus;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
    }

    public StoreStockHistory updateStoreStockHistoryStatus(StoreStockHistory storeStockHistory, WmsEnum.HistoryStatus historyStatus) {

        storeStockHistory.setHistoryStatus(historyStatus);

        return storeStockHistory;
    }
}














































