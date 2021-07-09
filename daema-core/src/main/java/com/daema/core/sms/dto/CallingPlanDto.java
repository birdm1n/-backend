package com.daema.core.sms.dto;

import com.daema.core.sms.domain.CallingPlan;
import com.daema.core.sms.domain.JoinInfo;
import com.daema.core.sms.domain.Payment;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallingPlanDto {

    private Long callingPlanId;
    private String name;


}
