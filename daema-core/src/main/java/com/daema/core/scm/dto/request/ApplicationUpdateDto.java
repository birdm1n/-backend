package com.daema.core.scm.dto.request;

import com.daema.core.scm.dto.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUpdateDto {

    private ApplicationDto applicationDto;
    private ApplicationBasicDto applicationBasicDto;
    private ApplicationCustomerDto applicationCustomerDto;
    private ApplicationJoinDto joinInfoDto;
    private ApplicationPaymentDto applicationPaymentDto;
    private ApplicationTaskBoardDto applicationTaskBoardDto;
}
