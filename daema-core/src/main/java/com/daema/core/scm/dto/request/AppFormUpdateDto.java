package com.daema.core.scm.dto.request;

import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppFormUpdateDto {

    private ScmEnum.TaskState.LogisState logisState;
    private ScmEnum.TaskState.ConsultState consultState;
    private ScmEnum.TaskState.OpeningState openingState;
    private BasicInfoDto basicInfoDto;
    private CustomerDto customerDto;
    private JoinInfoDto joinInfoDto;
    private PaymentDto paymentDto;
    private TaskUpdateBoardDto taskUpdateBoardDto;
}
