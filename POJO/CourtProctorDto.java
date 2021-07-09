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
public class CourtProctorDto {

    @ApiModelProperty(value = "���� �븮�� ���̵�", example = "0")
    private Long courtProctorId;

    @ApiModelProperty(value = "�̸���")
    private String email;

    @ApiModelProperty(value = "�̸�")
    private String name;

    @ApiModelProperty(value = "�޴��� ��ȣ", example = "0")
    private Integer phoneNum;

    @ApiModelProperty(value = "�ֹε�Ϲ�ȣ", example = "0")
    private Integer registNum;

    @ApiModelProperty(value = "����")
    private String relationship;


}
