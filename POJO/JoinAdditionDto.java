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
public class JoinAdditionDto {

    @ApiModelProperty(value = "���� �ΰ����� ���̵�", example = "0")
    private Long joinAddtionId;

    @ApiModelProperty(value = "�ΰ����� ���̵�", example = "0")
    private Long additionId;

    @ApiModelProperty(value = "���� ���̵�", example = "0")
    private Long joinInfoId;


}
