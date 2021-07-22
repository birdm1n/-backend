package com.daema.core.scm.dto.request;

import com.daema.core.scm.domain.delivery.AppFormDelivery;
import com.daema.core.scm.dto.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppFormCreateDto {
    private AppFormDto appFormDto;
    private BasicInfoDto basicInfoDto;
    private CustomerDto customerDto;
    private JoinInfoDto joinInfoDto;
    private PaymentDto paymentDto;
    private TaskBoardDto taskBoardDto;
    private AppFormDeliveryDto appFormDeliveryDto;
}
