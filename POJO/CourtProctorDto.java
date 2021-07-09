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
public class CourtProctorDto {

    @ApiModelProperty(value = "법정 대리인 아이디", example = "0")
    private Long courtProctorId;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "휴대폰 번호", example = "0")
    private Integer phoneNum;

    @ApiModelProperty(value = "주민등록번호", example = "0")
    private Integer registNum;

    @ApiModelProperty(value = "관계")
    private String relationship;


}
