package com.daema.core.scm.domain.payment;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Account {

    @Column(name = "bank", columnDefinition = "varchar(255) comment '은행'")
    private String bank;

    @Column(name = "account_num", columnDefinition = "INT comment '계좌 번호'")
    private int accountNo;

    @Column(name = "account_holder", columnDefinition = "varchar(255) comment '예금주'")
    private String accountHolder;

    @Column(name = "date_of_birth", columnDefinition = "INT comment '생년월일'")
    private int dateOfBirth;

    @Column(name = "relation", columnDefinition = "varchar(255) comment '관계'")
    private String relation;



}
