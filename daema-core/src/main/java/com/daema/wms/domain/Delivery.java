package com.daema.wms.domain;

import com.daema.base.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="deliveryId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    /**
     * 1 : 택배
     * 2 : 퀵
     * 3 : 직접전달
     */
    @Column(nullable = false, name = "delivery_type", columnDefinition ="char(1)")
    private String deliveryType;

    //택배사 codeSeq
    @Column(name = "courier")
    private Integer courier;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "delivery_memo")
    private String deliveryMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regi_user_id", referencedColumnName = "seq")
    private Member regiUserId;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private OutStock outStock;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private MoveStock moveStock;

    @Builder
    public Delivery(Long deliveryId, String deliveryType, Integer courier, String invoiceNo, String deliveryMemo
            ,Member regiUserId ,LocalDateTime regiDateTime){
        this.deliveryId = deliveryId;
        this.deliveryType = deliveryType;
        this.courier = courier;
        this.invoiceNo = invoiceNo;
        this.deliveryMemo = deliveryMemo;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
    }
}
