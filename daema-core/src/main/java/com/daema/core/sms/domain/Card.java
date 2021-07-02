package com.daema.core.sms.domain;

import com.daema.core.base.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "cardId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "card", comment = "카드")
public class Card  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", columnDefinition = "BIGINT UNSIGNED comment '카드 아이디'")
    private Long cardId;

    @Column(name = "card_info", columnDefinition = "varchar(255) comment '카드 정보'")
    private String cardInfo;

    @Column(name = "card_num", columnDefinition = "INT comment '카드 번호'")
    private int cardNo;

    @Column(name = "card_holder", columnDefinition = "varchar(255) comment '카드주'")
    private String cardHolder;

    @Column(name = "resident_registration_num", columnDefinition = "INT comment '주민등록번호'")
    private int residentRegistrationNo;

    @Column(name = "expiry_date", columnDefinition = "INT comment '유효기간'")
    private int expiryDate;
    // dataTime


    @OneToOne(mappedBy = "card")
    private Payment payment;
}
