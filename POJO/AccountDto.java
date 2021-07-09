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
public class AccountDto {

    @ApiModelProperty(value = "���� ���̵�", example = "0")
    private Long accountId;

    @ApiModelProperty(value = "������")
    private String accountHolder;

    @ApiModelProperty(value = "���� ��ȣ", example = "0")
    private Integer accountNum;

    @ApiModelProperty(value = "����")
    private String bank;

    @ApiModelProperty(value = "�������", example = "0")
    private Integer dateOfBirth;

    @ApiModelProperty(value = "����")
    private String relation;


}
