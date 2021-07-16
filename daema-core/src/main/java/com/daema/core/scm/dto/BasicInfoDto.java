package com.daema.core.scm.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicInfoDto {

    @ApiModelProperty(value = "기본정보 아이디")
    private Long basicInfoId;
/*
    @ApiModelProperty(value = "멤버",  required = true)*/
    private Long members;

}
