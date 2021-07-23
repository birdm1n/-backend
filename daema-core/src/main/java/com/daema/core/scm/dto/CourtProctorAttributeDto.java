package com.daema.core.scm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtProctorAttributeDto {


    @ApiModelProperty(value = "법정 대리인 이름")
    private String courtProctorName;

    @ApiModelProperty(value = "법정 대리인 연락처")
    private String courtProctorPhone;

    @ApiModelProperty(value = "법정 대리인 연락처1")
    private String courtProctorPhone1;

    @ApiModelProperty(value = "법정 대리인 연락처2")
    private String courtProctorPhone2;

    @ApiModelProperty(value = "법정 대리인 연락처3")
    private String courtProctorPhone3;

    @ApiModelProperty(value = "법정 대리인 등록 번호1")
    private String courtProctorRegiNum1;

    @ApiModelProperty(value = "법정 대리인 등록 번호2")
    private String courtProctorRegiNum2;

    @ApiModelProperty(value = "법정 대리인 관계")
    private String courtProctorRelation;


}
