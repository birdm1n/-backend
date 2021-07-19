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
public class AppFormRepDto {
    private BasicInfoDto basicInfoDto;
    private TaskUpdateBoardDto taskUpdateBoardDto;
    private CustomerDto customerDto;
    private JoinInfoDto joinInfoDto;
    private PaymentDto paymentDto;
}
