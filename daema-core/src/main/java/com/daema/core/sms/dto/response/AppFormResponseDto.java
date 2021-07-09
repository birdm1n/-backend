package com.daema.core.sms.dto.response;

import com.daema.core.sms.dto.CustomerDto;
import com.daema.core.sms.dto.JoinInfoDto;
import com.daema.core.sms.dto.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationFormResponseDto {

    private CustomerDto customerDto;
    private JoinInfoDto joinInfoDto;
    private PaymentDto paymentDto;
}
