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
public class AdditionDto {

    @ApiModelProperty(value = "부가서비스 아이디", example = "0")
    private Long additionId;

    @ApiModelProperty(value = "부가서비스 카테고리")
    private String additionCategory;

    @ApiModelProperty(value = "요금", example = "0")
    private Integer charge;

    @ApiModelProperty(value = "상품명")
    private String productName;


}
