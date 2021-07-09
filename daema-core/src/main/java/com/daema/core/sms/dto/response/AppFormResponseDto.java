package com.daema.core.sms.dto.response;

import com.daema.core.sms.dto.CustomerDto;
import com.daema.core.sms.dto.JoinInfoDto;
import com.daema.core.sms.dto.PaymentDto;
import com.daema.core.sms.dto.TaskUpdateBoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppFormResponseDto {

    private TaskUpdateBoardDto taskUpdateBoardDto;
    private CustomerDto customerDto;
    private JoinInfoDto joinInfoDto;
    private PaymentDto paymentDto;
}
