package com.daema.core.scm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtProctorDto {

    @ApiModelProperty(value = "법정 대리인 아이디")
    private Long courtProctorId;

    @ApiModelProperty(value = "법정 대리인 이름")
    private String name;

    @ApiModelProperty(value = "법정 대리인 이메일")
    private String email;
/*
    @ApiModelProperty(value = "")*/
    private int registNo;

    @ApiModelProperty(value = "휴대폰 번호")
    private int phoneNo;

    @ApiModelProperty(value = "관계")
    private String relationship;



}
