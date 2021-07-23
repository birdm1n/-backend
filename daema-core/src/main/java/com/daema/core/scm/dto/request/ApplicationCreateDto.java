package com.daema.core.scm.dto.request;

import com.daema.core.scm.dto.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCreateDto {
    private ApplicationDto applicationDto;
    private ApplicationBasicDto applicationBasicDto;
    private ApplicationCustomerDto applicationCustomerDto;
    private ApplicationJoinDto joinInfoDto;
    private ApplicationPaymentDto applicationPaymentDto;
    private ApplicationTaskBoardDto applicationTaskBoardDto;
    private ApplicationDeliveryDto applicationDeliveryDto;
}
