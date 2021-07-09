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
public class GiroDto {

    @ApiModelProperty(value = "지로 아이디", example = "0")
    private Long giroId;

    @ApiModelProperty(value = "주소")
    private String city;

    @ApiModelProperty(value = "상세주소")
    private String street;

    @ApiModelProperty(value = "우편번호")
    private String zipcode;


}
