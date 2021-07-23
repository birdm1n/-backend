package com.daema.core.scm.dto;

import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.domain.payment.AccountAttribute;
import com.daema.core.scm.domain.payment.GiroAttribute;
import com.daema.core.scm.domain.payment.CardAttribute;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationPaymentDto {
    @ApiModelProperty(value = "신청서 아이디", example = "0")
    private Long applId;

    @ApiModelProperty(value = "납부 유형",  required = true)
    private ScmEnum.PaymentWay paymentWay;

    @ApiModelProperty(value = "계좌")
    private AccountAttribute accountAttribute;

    @ApiModelProperty(value = "카드")
    private CardAttribute cardAttribute;

    @ApiModelProperty(value = "지로")
    private GiroAttribute giroAttribute;


}
