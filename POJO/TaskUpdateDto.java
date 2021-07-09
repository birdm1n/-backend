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
public class TaskUpdateDto {

    @ApiModelProperty(value = "null", example = "0")
    private Long taskUpdateId;

    @ApiModelProperty(value = "사유")
    private String reason;

    @ApiModelProperty(value = "업무 상태")
    private String taskState;


}
