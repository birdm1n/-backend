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
public class CustomerDto {

    @ApiModelProperty(value = "고객 아이디", example = "0")
    private Long customerId;

    @ApiModelProperty(value = "요금 감면", example = "0")
    private Integer chargeReduct;

    @ApiModelProperty(value = "주소")
    private String city;

    @ApiModelProperty(value = "상세주소")
    private String street;

    @ApiModelProperty(value = "우편번호")
    private String zipcode;

    @ApiModelProperty(value = "고객 유형")
    private String customerType;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "비상연락망", example = "0")
    private Integer emgPhone;

    @ApiModelProperty(value = "지역")
    private String area;

    @ApiModelProperty(value = "국가")
    private String contury;

    @ApiModelProperty(value = "만료일자", example = "0")
    private Integer expiredDate;

    @ApiModelProperty(value = "외국인 등록번호", example = "0")
    private Integer foreignRegiNum;

    @ApiModelProperty(value = "발급일자", example = "0")
    private Integer issueDate;

    @ApiModelProperty(value = "면허번호", example = "0")
    private Integer licenseNum;

    @ApiModelProperty(value = "신분증 유형")
    private String licenseType;

    @ApiModelProperty(value = "체류코드", example = "0")
    private Integer stayCode;

    @ApiModelProperty(value = "휴대폰 번호", example = "0")
    private Integer phoneNum;

    @ApiModelProperty(value = "주민등록번호", example = "0")
    private Integer registNum;

    @ApiModelProperty(value = "법정 대리인 아이디", example = "0")
    private Long courtProctorId;


}
