package com.daema.core.sms.dto;

import com.daema.core.sms.domain.Account;
import com.daema.core.sms.domain.Card;
import com.daema.core.sms.domain.Giro;
import com.daema.core.sms.domain.Payment;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private Long paymentId;
    private SmsEnum.PaymentWay paymentWay;
    private AccountDto accountDto;
    private CardDto cardDto;
    private GiroDto giroDto;


}
