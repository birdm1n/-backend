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


}
