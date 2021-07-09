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
public class AccountDto {

    @ApiModelProperty(value = "계좌 아이디", example = "0")
    private Long accountId;

    @ApiModelProperty(value = "예금주")
    private String accountHolder;

    @ApiModelProperty(value = "계좌 번호", example = "0")
    private Integer accountNum;

    @ApiModelProperty(value = "은행")
    private String bank;

    @ApiModelProperty(value = "생년월일", example = "0")
    private Integer dateOfBirth;

    @ApiModelProperty(value = "관계")
    private String relation;


}
