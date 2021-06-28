package com.daema.core.wms.domain;

import com.daema.core.base.domain.Members;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="storeStockChkId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "store_stock_check", comment = "관리점 재고 확인")
public class StoreStockCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_stock_chk_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 재고 확인 아이디'")
    private Long storeStockChkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_stock_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 재고 아이디'")
    private StoreStock storeStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regi_user_id", referencedColumnName = "member_id", columnDefinition = "BIGINT UNSIGNED comment '등록 유저 아이디'")
    private Members regiUserId;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    private LocalDateTime regiDateTime;

    @Builder
    public StoreStockCheck(Long storeStockChkId, StoreStock storeStock,
                           Members regiUserId, LocalDateTime regiDateTime){
        this.storeStockChkId = storeStockChkId;
        this.storeStock = storeStock;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
    }
}














































