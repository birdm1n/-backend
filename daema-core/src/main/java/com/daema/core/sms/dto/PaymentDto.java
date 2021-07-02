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
    private Account account;
    private Card card;
    private Giro giro;

    public static PaymentDto from(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .paymentWay(payment.getPaymentWay())
                .account(payment.getAccount())
                .card(payment.getCard())
                .giro(payment.getGiro())
                .build();
    }

    public static Payment toEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .paymentId(paymentDto.getPaymentId())
                .paymentWay(paymentDto.getPaymentWay())
                .account(paymentDto.getAccount())
                .card(paymentDto.getCard())
                .giro(paymentDto.getGiro())
                .build();
    }

}
