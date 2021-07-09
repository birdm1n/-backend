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

    @ApiModelProperty(value = "카드 아이디", example = "0")
    private Long cardId;

    @ApiModelProperty(value = "카드주")
    private String cardHolder;

    @ApiModelProperty(value = "카드 정보")
    private String cardInfo;

    @ApiModelProperty(value = "카드 번호", example = "0")
    private Integer cardNum;

    @ApiModelProperty(value = "유효기간", example = "0")
    private Integer expiryDate;

    @ApiModelProperty(value = "주민등록번호", example = "0")
    private Integer residentRegistrationNum;


}
