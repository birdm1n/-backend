package com.daema.core.sms.domain;

import com.daema.core.base.domain.common.BaseEntity;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "paymentId")
@NoArgsConstructor
@AllArgsConstructor
@Entity  //  aggregate ...
@org.hibernate.annotations.Table(appliesTo = "payment", comment = "납부")
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", columnDefinition = "BIGINT UNSIGNED comment '납부 아이디'")
    private Long paymentId;


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_way", columnDefinition = "varchar(255) comment '납부 방법'")
    private SmsEnum.PaymentWay paymentWay;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", columnDefinition = "BIGINT UNSIGNED comment '계좌'", nullable = true)
    private Account account;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", columnDefinition = "BIGINT UNSIGNED comment '카드'", nullable = true)
    private Card card;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "giro_id", columnDefinition = "BIGINT UNSIGNED comment '지로'", nullable = true)
    private Giro giro;



    }

