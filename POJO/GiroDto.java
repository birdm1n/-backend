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
public class GiroDto {

    @ApiModelProperty(value = "���� ���̵�", example = "0")
    private Long giroId;

    @ApiModelProperty(value = "�ּ�")
    private String city;

    @ApiModelProperty(value = "���ּ�")
    private String street;

    @ApiModelProperty(value = "�����ȣ")
    private String zipcode;


}
