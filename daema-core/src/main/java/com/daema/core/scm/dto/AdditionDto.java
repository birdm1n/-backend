package com.daema.core.scm.dto;


import com.daema.core.scm.domain.enums.ScmEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionDto {

    @ApiModelProperty(value = "부가서비스 아이디",  required = true)
    private Long additionId;

    @ApiModelProperty(value = "부가서비스 카테고리",  required = true)
    private ScmEnum.AdditionCategory additionCategory;

    @ApiModelProperty(value = "상품 이름",  required = true)
    private String productName;

    @ApiModelProperty(value = "가격",  required = true)
    private int charge;



}
