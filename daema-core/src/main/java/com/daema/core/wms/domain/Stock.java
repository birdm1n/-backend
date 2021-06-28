package com.daema.core.wms.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of="stockId")
@ToString(exclude = {"prevMoveStockList", "nextMoveStockList", "prevStoreStockList", "nextStoreStockList", "prevOutStockList", "childStockList"})
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "stock", comment = "재고")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id", columnDefinition = "BIGINT UNSIGNED comment '재고 아이디'")
    private long stockId;

    @NotBlank
    @Column(name = "stock_name", columnDefinition = "varchar(255) comment '재고 이름'")
    private String stockName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_stock_id", columnDefinition = "BIGINT UNSIGNED comment '부모 재고 아이디'")
    private Stock parentStock;

    @OneToMany(mappedBy = "parentStock")
    private List<Stock> childStockList = new ArrayList<>();

    /**
     * 개통점 openStoreId, 영업점 storeId, Login storeId
     */
    @Column(nullable = false, name="store_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 아이디'")
    private long storeId;

    /**
     * O - openStore, S - store, I - inner (내부 관리)
     */
    @Column(nullable = false, name="stock_type", columnDefinition = "varchar(255) comment '재고 타입'")
    private String stockType;

    @Column(nullable = false, name="regi_store_id", columnDefinition = "BIGINT UNSIGNED comment '등록 관리점 아이디'")
    private long regiStoreId;

    @Column(name = "charger_name", columnDefinition = "varchar(255) comment '담당자 이름'")
    private String chargerName;

    @Column( name = "charger_phone", columnDefinition = "varchar(255) comment '담당자 연락처'")
    private String chargerPhone;

    @Column(name = "charger_phone1", columnDefinition = "varchar(255) comment '담당자 연락처1'")
    private String chargerPhone1;

    @Column(name = "charger_phone2", columnDefinition = "varchar(255) comment '담당자 연락처2'")
    private String chargerPhone2;

    @Column(name = "charger_phone3", columnDefinition = "varchar(255) comment '담당자 연락처3'")
    private String chargerPhone3;

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1) comment '삭제 여부'")
    @ColumnDefault("\"N\"")
    private String delYn;

    @Column(name = "regi_user_id", columnDefinition = "BIGINT UNSIGNED comment '등록 유저 아이디'")
    private long regiUserId;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    private LocalDateTime regiDateTime;

    @Column(name = "upd_user_id", columnDefinition = "BIGINT UNSIGNED comment '수정 유저 아이디'")
    private Long updUserId;

    @Column(name = "upd_datetime", columnDefinition = "DATETIME(6) comment '수정 일시'")
    private LocalDateTime updDateTime;

    @OneToMany(mappedBy = "prevStock")
    private List<MoveStock> prevMoveStockList = new ArrayList<>();

    @OneToMany(mappedBy = "nextStock")
    private List<MoveStock> nextMoveStockList = new ArrayList<>();

    @OneToMany(mappedBy = "prevStock")
    private List<StoreStock> prevStoreStockList = new ArrayList<>();

    @OneToMany(mappedBy = "nextStock")
    private List<StoreStock> nextStoreStockList = new ArrayList<>();

    @OneToMany(mappedBy = "prevStock")
    private List<OutStock> prevOutStockList = new ArrayList<>();

    @Builder
    public Stock(long stockId, String stockName, Stock parentStock, long storeId, String stockType, String chargerName
            , String chargerPhone, String chargerPhone1, String chargerPhone2, String chargerPhone3
            , long regiStoreId, String delYn, long regiUserId, LocalDateTime regiDateTime
            , Long updUserId, LocalDateTime updDateTime){
        this.stockId = stockId;
        this.stockName = stockName;
        this.parentStock = parentStock;
        this.storeId = storeId;
        this.stockType = stockType;
        this.chargerName = chargerName;
        this.chargerPhone = chargerPhone;
        this.chargerPhone1 = chargerPhone1;
        this.chargerPhone2 = chargerPhone2;
        this.chargerPhone3 = chargerPhone3;
        this.regiStoreId = regiStoreId;
        this.delYn = delYn;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
        this.updUserId = updUserId;
        this.updDateTime = updDateTime;
    }

    public void updateDelYn(Stock provider, String delYn, long updUserId){
        provider.setDelYn(delYn);
        provider.setUpdUserId(updUserId);
        provider.setUpdDateTime(LocalDateTime.now());
    }
}
