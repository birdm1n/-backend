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
public class AddServiceDto {

    @ApiModelProperty(value = "�ΰ����� ���̵�", example = "0")
    private Long addServiceId;

    @ApiModelProperty(value = "�ΰ����� ī�װ�")
    private String addServiceCategory;

    @ApiModelProperty(value = "���", example = "0")
    private Integer charge;

    @ApiModelProperty(value = "��ǰ��")
    private String productName;


}
