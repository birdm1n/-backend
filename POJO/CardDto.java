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
public class CardDto {

    @ApiModelProperty(value = "ī�� ���̵�", example = "0")
    private Long cardId;

    @ApiModelProperty(value = "ī����")
    private String cardHolder;

    @ApiModelProperty(value = "ī�� ����")
    private String cardInfo;

    @ApiModelProperty(value = "ī�� ��ȣ", example = "0")
    private Integer cardNum;

    @ApiModelProperty(value = "��ȿ�Ⱓ", example = "0")
    private Integer expiryDate;

    @ApiModelProperty(value = "�ֹε�Ϲ�ȣ", example = "0")
    private Integer residentRegistrationNum;


}
