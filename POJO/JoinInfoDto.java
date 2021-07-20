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
public class JoinInfoDto {

    @ApiModelProperty(value = "���� ���̵�", example = "0")
    private Long joinInfoId;

    @ApiModelProperty(value = "���ǸŰ�", example = "0")
    private Integer actuallSellPrice;

    @ApiModelProperty(value = "���� �Ⱓ")
    private String aggremntPeriod;

    @ApiModelProperty(value = "���� ����")
    private String aggrementType;

    @ApiModelProperty(value = "�Ա� ���� �ݾ�", example = "0")
    private Integer amtDeposited;

    @ApiModelProperty(value = "���� ��ȣ", example = "0")
    private Integer certNum;

    @ApiModelProperty(value = "�Ա� ����")
    private String depositYn;

    @ApiModelProperty(value = "��Ÿ���α�", example = "0")
    private Integer etcDiscountAmt;

    @ApiModelProperty(value = "�߰�������", example = "0")
    private Integer extraSupAmt;

    @ApiModelProperty(value = "�����Һ�", example = "0")
    private Integer freeInstl;

    @ApiModelProperty(value = "�Һ� �Ⱓ")
    private String insPeriod;

    @ApiModelProperty(value = "���� ����")
    private String joinType;

    @ApiModelProperty(value = "�� �⺻��", example = "0")
    private Integer monthBasicPrice;

    @ApiModelProperty(value = "�� �ܸ��� �ݾ�", example = "0")
    private Integer monthDevicePrice;

    @ApiModelProperty(value = "�� ���αݾ�", example = "0")
    private Integer monthPaymentPrice;

    @ApiModelProperty(value = "���� ��� ��ȣ")
    private String openHopePhoneNum;

    @ApiModelProperty(value = "���� �޴��� ��ȣ", example = "0")
    private Integer openPhoneNum;

    @ApiModelProperty(value = "���", example = "0")
    private Integer outStockPrice;

    @ApiModelProperty(value = "����������", example = "0")
    private Integer pubNotiSupAmt;

    @ApiModelProperty(value = "����")
    private String usim;

    @ApiModelProperty(value = "���� �Ϸù�ȣ")
    private String usimSn;

    @ApiModelProperty(value = "�����", example = "0")
    private Long callingPlanId;

    @ApiModelProperty(value = "��ǰ", example = "0")
    private Long goodsId;

    @ApiModelProperty(value = "������", example = "0")
    private Long openingStoreId;


}
