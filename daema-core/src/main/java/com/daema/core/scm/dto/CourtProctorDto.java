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

    @ApiModelProperty(value = "대리인 연락처")
    private String courtProctorPhone;

    @ApiModelProperty(value = "대리인 연락처1")
    private String courtProctorPhone1;

    @ApiModelProperty(value = "대리인 연락처2")
    private String courtProctorPhone2;

    @ApiModelProperty(value = "대리인 연락처3")
    private String courtProctorPhone3;

    @ApiModelProperty(value = "관계")
    private String relationship;



}
