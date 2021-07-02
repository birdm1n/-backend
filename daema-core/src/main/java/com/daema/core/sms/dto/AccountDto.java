package com.daema.core.sms.dto;

import com.daema.core.sms.domain.Account;
import com.daema.core.sms.domain.Payment;
import com.daema.core.wms.domain.StoreStock;
import com.daema.core.wms.dto.StoreStockMgmtDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long accountId;
    private String bank;
    private int accountNo;
    private String accountHolder;
    private int dateOfBirth;
    private String relation;
    private Payment payment;

    public static AccountDto from(Account account) {
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .bank(account.getBank())
                .accountNo(account.getAccountNo())
                .accountHolder(account.getAccountHolder())
                .dateOfBirth(account.getDateOfBirth())
                .relation(account.getRelation())
                .payment(account.getPayment())
                .build();
    }

    public static Account toEntity(AccountDto accountDto) {
        return Account.builder()
                .accountId(accountDto.getAccountId())
                .bank(accountDto.getBank())
                .accountNo(accountDto.getAccountNo())
                .accountHolder(accountDto.getAccountHolder())
                .dateOfBirth(accountDto.getDateOfBirth())
                .relation(accountDto.getRelation())
                .payment(accountDto.getPayment())
                .build();
    }
}
