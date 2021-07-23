package com.daema.core.scm.dto;

import com.daema.core.scm.domain.customer.CustomerCourtProctorAttribute;
import com.daema.core.scm.domain.vo.Address;
import com.daema.core.scm.domain.customer.LicenseAuth;
import com.daema.core.scm.domain.enums.ScmEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationCustomerDto {

    @ApiModelProperty(value = "신청서 아이디", example = "0")
    private Long applId;

    @ApiModelProperty(value = "가입 번호",  required = true)
    private int registNo;

    @ApiModelProperty(value = "비용 감면",  required = true)
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
    private ScmEnum.CustomerType customerType;

    @ApiModelProperty(value = "신분증 진위",  required = true)
    private LicenseAuth licenseAuth;

    private CustomerCourtProctorAttribute customerCourtProctorAttribute;



}
