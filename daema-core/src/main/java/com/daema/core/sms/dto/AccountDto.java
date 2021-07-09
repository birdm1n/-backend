package com.daema.core.sms.dto;

import com.daema.core.commgmt.domain.GoodsOption;
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
    private int dateOfBirth;
    private String accountHolder;
    private String relation;


    public static AccountDto from(Account account) {
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .bank(account.getBank())
                .accountNo(account.getAccountNo())
                .accountHolder(account.getAccountHolder())
                .dateOfBirth(account.getDateOfBirth())
                .relation(account.getRelation())
              /*  .paymentId(account.getPayment().getPaymentId())*/
                .build();
    }


}
