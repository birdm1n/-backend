package com.daema.core.scm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallingPlanDto {

    @ApiModelProperty(value = "요금제 아이디",  required = true)
    private Long callingPlanId;
    /*
    @ApiModelProperty(value = "요금제 이름",  required = true)*/
    private String name;


}
