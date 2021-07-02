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
    private Payment payment;

    public static CardDto from(Card card) {
        return CardDto.builder()
               .cardId(card.getCardId())
                .cardInfo(card.getCardInfo())
                .cardNo(card.getCardNo())
                .cardHolder(card.getCardHolder())
                .residentRegistrationNo(card.getResidentRegistrationNo())
                .expiryDate(card.getExpiryDate())
                .payment(card.getPayment())
                .build();
    }

    public static Card toEntity(CardDto cardDto) {
        return Card.builder()
                .cardId(cardDto.getCardId())
                .cardInfo(cardDto.getCardInfo())
                .cardNo(cardDto.getCardNo())
                .cardHolder(cardDto.getCardHolder())
                .residentRegistrationNo(cardDto.getResidentRegistrationNo())
                .expiryDate(cardDto.getExpiryDate())
                .payment(cardDto.getPayment())
                .build();
    }
}
