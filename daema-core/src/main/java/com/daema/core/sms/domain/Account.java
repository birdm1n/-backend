package com.daema.core.sms.domain;

import com.daema.core.base.domain.common.BaseEntity;
import com.daema.core.sms.dto.AccountDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "accountId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "account", comment = "계좌")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", columnDefinition = "BIGINT UNSIGNED comment '계좌 아이디'")
    private Long accountId;

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

    @OneToOne(mappedBy = "account")
    private Payment payment;


    public static Account toEntity(AccountDto accountDto) {
        return Account.builder()
                .accountId(accountDto.getAccountId())
                .bank(accountDto.getBank())
                .accountNo(accountDto.getAccountNo())
                .accountHolder(accountDto.getAccountHolder())
                .dateOfBirth(accountDto.getDateOfBirth())
                .relation(accountDto.getRelation())
                .build();
    }


}
