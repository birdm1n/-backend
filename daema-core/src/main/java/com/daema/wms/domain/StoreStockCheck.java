package com.daema.wms.domain;

import com.daema.base.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="storeStockChkId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="store_stock_check")
public class StoreStockCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_stock_chk_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 재고 확인 아이디'")
    private Long storeStockChkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_stock_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 재고 아이디'")
    private StoreStock storeStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regi_user_id", referencedColumnName = "seq", columnDefinition = "BIGINT UNSIGNED comment '등록 유저 아이디'")
    private Member regiUserId;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 날짜시간'")
    private LocalDateTime regiDateTime;

    @Builder
    public StoreStockCheck(Long storeStockChkId, StoreStock storeStock,
                Member regiUserId, LocalDateTime regiDateTime){
        this.storeStockChkId = storeStockChkId;
        this.storeStock = storeStock;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
    }
}














































