package com.daema.core.sms.dto;

import com.daema.core.sms.domain.Giro;
import com.daema.core.sms.domain.Payment;
import com.daema.core.sms.domain.VO.Address;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiroDto {
    private Long giroId;
    private Address paymentAddress;
    private Payment payment;

    public static GiroDto from(Giro giro) {
        return GiroDto.builder()
                .giroId(giro.getGiroId())
                .paymentAddress(giro.getPaymentAddress())
                .payment(giro.getPayment())
                .build();
    }

    public static Giro toEntity(GiroDto giroDto) {
        return Giro.builder()
                .giroId(giroDto.getGiroId())
                .paymentAddress(giroDto.getPaymentAddress())
                .payment(giroDto.getPayment())
                .build();
    }
}
