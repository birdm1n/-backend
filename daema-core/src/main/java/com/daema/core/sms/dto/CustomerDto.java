package com.daema.core.sms.dto;

import com.daema.core.sms.domain.CourtProctor;
import com.daema.core.sms.domain.Customer;
import com.daema.core.sms.domain.VO.Address;
import com.daema.core.sms.domain.VO.LicenseAuth;
import com.daema.core.sms.domain.enums.SmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    @ApiModelProperty(value = "고객 아이디",  required = true)
    private Long customerId;

    @ApiModelProperty(value = "가입 번호",  required = true)
    private int registNo;

    @ApiModelProperty(value = "차지 리덕트",  required = true)
    private int chargeReduct;

    @ApiModelProperty(value = "휴대폰 번호",  required = true)
    private int phoneNo;

    @ApiModelProperty(value = "응급 전화",  required = true)
    private int emgPhone;

    @ApiModelProperty(value = "이메일",  required = true)
    private String email;

    @ApiModelProperty(value = "고객 주소",  required = true)
    private Address cusAddress;

    @ApiModelProperty(value = "고객 유형",  required = true)
    private SmsEnum.CustomerType customerType;

    @ApiModelProperty(value = "신분증 진위",  required = true)
    private LicenseAuth licenseAuth;

    private CourtProctorDto courtProctorDto;



}
