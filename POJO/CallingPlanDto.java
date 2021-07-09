package com.daema.core.scm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallingPlanDto {

    @ApiModelProperty(value = "요금제 아이디", example = "0")
    private Long callingPlanId;

    @ApiModelProperty(value = "요금제 명")
    private String name;


}
