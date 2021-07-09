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
public class PaymentDto {

    @ApiModelProperty(value = "���� ���̵�", example = "0")
    private Long paymentId;

    @ApiModelProperty(value = "���� ���")
    private String paymentWay;

    @ApiModelProperty(value = "����", example = "0")
    private Long accountId;

    @ApiModelProperty(value = "ī��", example = "0")
    private Long cardId;

    @ApiModelProperty(value = "����", example = "0")
    private Long giroId;


}
