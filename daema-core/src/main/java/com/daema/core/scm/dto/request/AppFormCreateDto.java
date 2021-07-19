package com.daema.core.scm.dto.request;

import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppFormCreateDto {

    private BasicInfoDto basicInfoDto;
    private CustomerDto customerDto;
    private JoinInfoDto joinInfoDto;
    private PaymentDto paymentDto;
    private TaskUpdateBoardDto taskUpdateBoardDto;
}
