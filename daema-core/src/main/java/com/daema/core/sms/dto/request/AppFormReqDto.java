package com.daema.core.sms.dto.request;

import com.daema.core.sms.domain.CallingPlan;
import com.daema.core.sms.domain.JoinInfo;
import com.daema.core.sms.dto.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFormReqDto {
    private CustomerDto customerDto;
    private JoinInfoDto joinInfoDto;
    private PaymentDto paymentDto;
}
