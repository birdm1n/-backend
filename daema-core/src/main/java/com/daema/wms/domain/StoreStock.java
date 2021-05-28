package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "storeStockId")
@ToString
@NoArgsConstructor
@Entity
@Table(name = "store_stock")
public class StoreStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_stock_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 재고 아이디'")
    private Long storeStockId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 아이디'")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id", columnDefinition = "BIGINT UNSIGNED comment '기기 아이디'")
    private Device device;

    @Column(name = "stock_type", columnDefinition = "varchar(255) comment '재고 타입'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockType stockType;

    /**
     * WmsEnum.StockType 에 따라 테이블 join 한다
     */
    @Column(name = "stock_type_id", columnDefinition = "BIGINT UNSIGNED comment '재고 타입 아이디'")
    private Long stockTypeId;

    /**
     * 재고여부. Y-보유 재고, N-소멸 재고
     */
    @Column(nullable = false, name = "stock_yn", columnDefinition = "char(1) comment '재고 여부'")
    @ColumnDefault("\"Y\"")
    private String stockYn = "Y";

    @Column(name = "check_date_time1", columnDefinition = "DATETIME(6) comment '확인 날짜 시간1'")
    private LocalDateTime checkDateTime1;

    @Column(name = "check_date_time2", columnDefinition = "DATETIME(6) comment '확인 날짜 시간2'")
    private LocalDateTime checkDateTime2;

    /**
     * 이전 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_stock_id", referencedColumnName = "stock_id", columnDefinition = "BIGINT UNSIGNED comment '이전 보유처 아이디'")
    private Stock prevStock;

    /**
     * 현재 보유처
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_stock_id", referencedColumnName = "stock_id", columnDefinition = "BIGINT UNSIGNED comment '현재 보유처 아이디'")
    private Stock nextStock;

    @Transient
    private WmsEnum.HistoryStatus historyStatus;

    @Builder
    public StoreStock(Long storeStockId, Store store, WmsEnum.StockType stockType, Device device,
                      String stockYn, Long stockTypeId, Stock prevStock, Stock nextStock
            , LocalDateTime checkDateTime1, LocalDateTime checkDateTime2) {
        this.storeStockId = storeStockId;
        this.store = store;
        this.stockType = stockType;
        this.stockYn = stockYn;
        this.stockTypeId = stockTypeId;
        this.device = device;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
        this.checkDateTime1 = checkDateTime1;
        this.checkDateTime2 = checkDateTime2;
    }

    public StoreStock updateStoreStock(StoreStock storeStock, StoreStock setData) {

        storeStock.setStore(setData.getStore());
        storeStock.setDevice(setData.getDevice());
        storeStock.setStockType(setData.getStockType());
        storeStock.setStockTypeId(setData.getStockTypeId());
        storeStock.setStockYn(setData.getStockYn());
        storeStock.setPrevStock(setData.getPrevStock());
        storeStock.setNextStock(setData.getNextStock());

        return storeStock;
    }

    //재고확인일을 업데이트 처리
    public StoreStock updateStoreStockCheck(StoreStock storeStock) {

        if (storeStock.getCheckDateTime1() != null) {

            if (storeStock.getCheckDateTime2() != null) {
                storeStock.setCheckDateTime1(storeStock.getCheckDateTime2());
            }

            storeStock.setCheckDateTime2(LocalDateTime.now());

        } else {
            storeStock.setCheckDateTime1(LocalDateTime.now());
        }

        return storeStock;
    }

    public StoreStockHistory toHistoryEntity(StoreStock storeStock){
        return StoreStockHistory.builder()
                .storeStockHistoryId(0L)
                .storeStock(storeStock)
                .store(storeStock.getStore())
                .device(storeStock.getDevice())
                .stockType(storeStock.getStockType())
                .stockTypeId(storeStock.getStockTypeId())
                .historyStatus(storeStock.getHistoryStatus())
                .prevStock(storeStock.getPrevStock())
                .nextStock(storeStock.getNextStock())
                .stockYn(storeStock.getStockYn())
                .build();
    }
}














































