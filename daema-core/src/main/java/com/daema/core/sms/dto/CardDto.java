package com.daema.core.sms.dto;

import com.daema.core.sms.domain.Card;
import com.daema.core.sms.domain.Payment;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

    private Long cardId;
    private String cardInfo;
    private int cardNo;
    private String cardHolder;
    private int residentRegistrationNo;
    private int expiryDate;
  /*  private Long paymentId;*/


}
