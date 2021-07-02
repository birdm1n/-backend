package com.daema.core.sms.dto;

import com.daema.core.sms.domain.CallingPlan;
import com.daema.core.sms.domain.JoinInfo;
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
    private JoinInfo joinInfo;

    public static CallingPlanDto from(CallingPlan callingPlan) {
        return CallingPlanDto.builder()
                .callingPlanId(callingPlan.getCallingPlanId())
                .name(callingPlan.getName())
                .joinInfo(callingPlan.getJoinInfo())
                .build();
    }

    public static CallingPlan toEntity(CallingPlanDto callingPlanDto) {
        return CallingPlan.builder()
                .callingPlanId(callingPlanDto.getCallingPlanId())
                .name(callingPlanDto.getName())
                .joinInfo(callingPlanDto.getJoinInfo())
                .build();
    }
}
