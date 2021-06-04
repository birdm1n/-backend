package com.daema.wms.domain;

import com.daema.base.domain.common.BaseUserInfoEntity;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "storeStockHistoryId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "store_stock_history", comment = "관리점 재고 이력")
public class StoreStockHistory extends BaseUserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_stock_history_id", columnDefinition = "BIGINT unsigned comment '관리점 재고 이력 아이디'")
    private Long storeStockHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_stock_id", columnDefinition = "BIGINT unsigned comment '관리점 재고 아이디'")
    private StoreStock storeStock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id", columnDefinition = "BIGINT unsigned comment '기기 아이디'")
    private Device device;

    @Column(name = "stock_type", columnDefinition = "varchar(255) comment '관리점 타입'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockType stockType;

    @Column(name = "stock_type_id", columnDefinition = "BIGINT unsigned comment '관리점 타입 아이디'")
    private Long stockTypeId;

    @Column(name = "history_status", columnDefinition = "varchar(255) comment '이력 상태'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.HistoryStatus historyStatus;

    @Column(nullable = false, name = "stock_yn", columnDefinition = "char(1) comment '재고 여부'")
    @ColumnDefault("\"Y\"")
    private String stockYn = "Y";

    /**
     * 이전 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_stock_id", referencedColumnName = "stock_id", columnDefinition = "BIGINT unsigned comment '이전 재고 아이디'")
    private Stock prevStock;

    /**
     * 현재 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_stock_id", referencedColumnName = "stock_id", columnDefinition = "BIGINT unsigned comment '다음 재고 아이디'")
    private Stock nextStock;

    @Builder
    public StoreStockHistory(Long storeStockHistoryId, StoreStock storeStock, Store store, WmsEnum.StockType stockType, Device device,
                             Long stockTypeId, Stock prevStock, Stock nextStock, String stockYn, WmsEnum.HistoryStatus historyStatus) {
        this.storeStockHistoryId = storeStockHistoryId;
        this.storeStock = storeStock;
        this.store = store;
        this.device = device;
        this.stockType = stockType;
        this.stockTypeId = stockTypeId;
        this.historyStatus = historyStatus;
        this.stockYn = stockYn;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
    }

    public StoreStockHistory updateStoreStockHistoryStatus(StoreStockHistory storeStockHistory, WmsEnum.HistoryStatus historyStatus) {

        storeStockHistory.setHistoryStatus(historyStatus);

        return storeStockHistory;
    }
}














































