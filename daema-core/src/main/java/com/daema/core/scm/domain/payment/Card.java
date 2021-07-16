package com.daema.core.scm.domain.payment;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Card  {

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



}
