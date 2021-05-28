package com.daema.wms.domain;

import com.daema.base.domain.Member;
import com.daema.commgmt.domain.Store;
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
    @Column(name = "return_stock_id", columnDefinition = "BIGINT unsigned comment '반품 재고 아이디'")
    private Long returnStockId;


    /**
     * 반품비
     */
    @Column(name = "return_stock_amt", columnDefinition = "varchar(255) comment '반품 재고 금액'")
    private Integer returnStockAmt;

    @Column(name = "return_stock_memo", columnDefinition = "varchar(255) comment '반품 재고 메모'")
    private String returnStockMemo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regi_user_id", referencedColumnName = "seq", columnDefinition = "BIGINT unsigned comment '등록 유저 아이디'")
    private Member regiUserId;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 날짜시간'")
    private LocalDateTime regiDateTime;

    @Nullable
    @Column(name = "del_yn", columnDefinition ="char(1) comment '삭제 여부'")
    @ColumnDefault("\"N\"")
    private String delYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id", columnDefinition = "BIGINT unsigned comment '기기 아이디'")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_status_id", columnDefinition = "BIGINT unsigned comment '기기 상태 아이디'")
    private DeviceStatus returnDeviceStatus;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT unsigned comment '재고 아이디'")
    private Store store;

    @Builder
    public ReturnStock(Long returnStockId, Integer returnStockAmt
            , String returnStockMemo, Device device, DeviceStatus returnDeviceStatus
            ,Stock prevStock, Stock nextStock, Store store
            ,Member regiUserId ,LocalDateTime regiDateTime
    ,String delYn){
        this.returnStockId = returnStockId;
        this.returnStockAmt = returnStockAmt;
        this.returnStockMemo = returnStockMemo;
        this.device = device;
        this.returnDeviceStatus = returnDeviceStatus;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
        this.store = store;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
        this.delYn = delYn;
    }

    public void updateDelYn(ReturnStock returnStock, String delYn){
        returnStock.setDelYn(delYn);
    }
}
