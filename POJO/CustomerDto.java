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
public class CustomerDto {

    @ApiModelProperty(value = "�� ���̵�", example = "0")
    private Long customerId;

    @ApiModelProperty(value = "��� ����", example = "0")
    private Integer chargeReduct;

    @ApiModelProperty(value = "�ּ�")
    private String city;

    @ApiModelProperty(value = "���ּ�")
    private String street;

    @ApiModelProperty(value = "�����ȣ")
    private String zipcode;

    @ApiModelProperty(value = "�� ����")
    private String customerType;

    @ApiModelProperty(value = "�̸���")
    private String email;

    @ApiModelProperty(value = "��󿬶���", example = "0")
    private Integer emgPhone;

    @ApiModelProperty(value = "����")
    private String area;

    @ApiModelProperty(value = "����")
    private String contury;

    @ApiModelProperty(value = "��������", example = "0")
    private Integer expiredDate;

    @ApiModelProperty(value = "�ܱ��� ��Ϲ�ȣ", example = "0")
    private Integer foreignRegiNum;

    @ApiModelProperty(value = "�߱�����", example = "0")
    private Integer issueDate;

    @ApiModelProperty(value = "�����ȣ", example = "0")
    private Integer licenseNum;

    @ApiModelProperty(value = "�ź��� ����")
    private String licenseType;

    @ApiModelProperty(value = "ü���ڵ�", example = "0")
    private Integer stayCode;

    @ApiModelProperty(value = "�޴��� ��ȣ", example = "0")
    private Integer phoneNum;

    @ApiModelProperty(value = "�ֹε�Ϲ�ȣ", example = "0")
    private Integer registNum;

    @ApiModelProperty(value = "���� �븮�� ���̵�", example = "0")
    private Long courtProctorId;


}
