package com.daema.core.scm.dto.response;

import com.daema.core.scm.dto.CustomerDto;
import com.daema.core.scm.dto.JoinInfoDto;
import com.daema.core.scm.dto.PaymentDto;
import com.daema.core.scm.dto.TaskUpdateBoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppFormRepDto {

    private TaskUpdateBoardDto taskUpdateBoardDto;
    private CustomerDto customerDto;
    private JoinInfoDto joinInfoDto;
    private PaymentDto paymentDto;
}
