package com.daema.core.scm.dto.response;

import com.daema.core.scm.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationReqDto {
    private ApplicationBasicDto applicationBasicDto;
    private ApplicationTaskBoardDto applicationTaskBoardDto;
    private ApplicationCustomerDto applicationCustomerDto;
    private ApplicationJoinDto joinInfoDto;
    private ApplicationPaymentDto applicationPaymentDto;
}
