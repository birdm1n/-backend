package com.daema.core.scm.dto;

import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.domain.payment.Account;
import com.daema.core.scm.domain.payment.Giro;
import com.daema.core.scm.domain.payment.Card;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    @ApiModelProperty(value = "납부 아이디")
    private Long paymentId;

    @ApiModelProperty(value = "납부 유형",  required = true)
    private ScmEnum.PaymentWay paymentWay;

    @ApiModelProperty(value = "계좌")
    private Account account;

    @ApiModelProperty(value = "카드")
    private Card card;

    @ApiModelProperty(value = "지로")
    private Giro giro;


}
