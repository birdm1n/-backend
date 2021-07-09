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

    @ApiModelProperty(value = "납부 아이디", example = "0")
    private Long paymentId;

    @ApiModelProperty(value = "납부 방법")
    private String paymentWay;

    @ApiModelProperty(value = "계좌", example = "0")
    private Long accountId;

    @ApiModelProperty(value = "카드", example = "0")
    private Long cardId;

    @ApiModelProperty(value = "지로", example = "0")
    private Long giroId;


}
