package com.daema.wms.domain;

import com.daema.base.domain.Member;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="returnStockId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="return_stock")
public class ReturnStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_stock_id")
    private Long returnStockId;


    /**
     * 반품비
     */
    @Column(name = "return_stock_amt")
    private Integer returnStockAmt;

    @Column(name = "return_stock_memo")
    private String returnStockMemo;

    /**
     * 출고가 차감 YN
     */
    @Nullable
    @Column(name = "ddct_release_amt_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String ddctReleaseAmtYn = "N";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regi_user_id", referencedColumnName = "seq")
    private Member regiUserId;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_status_id")
    private DeviceStatus returnDeviceStatus;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public ReturnStock(Long returnStockId, Integer returnStockAmt
            , String returnStockMemo, String ddctReleaseAmtYn, Device device, DeviceStatus returnDeviceStatus
            ,Stock prevStock, Stock nextStock, Store store
            ,Member regiUserId ,LocalDateTime regiDateTime){
        this.returnStockId = returnStockId;
        this.returnStockAmt = returnStockAmt;
        this.returnStockMemo = returnStockMemo;
        this.ddctReleaseAmtYn = ddctReleaseAmtYn;
        this.device = device;
        this.returnDeviceStatus = returnDeviceStatus;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
        this.store = store;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
    }
}
