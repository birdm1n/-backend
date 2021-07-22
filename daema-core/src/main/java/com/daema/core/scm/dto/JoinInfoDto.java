package com.daema.core.scm.dto;

import com.daema.core.scm.domain.enums.ScmEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinInfoDto {

    @ApiModelProperty(value = "가입 정보 아이디")
    private Long joinInfoId;

    @ApiModelProperty(value = "개통 휴대폰 번호",  required = true)
    private int openPhoneNo;

    @ApiModelProperty(value = "개통 희망 휴대폰 번호",  required = true)
    private String openHopePhoneNo;

    @ApiModelProperty(value = "가입 유형",  required = true)
    private ScmEnum.JoinType joinType;

    @ApiModelProperty(value = "인증 번호",  required = true)
    private int certNo;

    @ApiModelProperty(value = "유심",  required = true)
    private ScmEnum.Usim usim;

    @ApiModelProperty(value = "유심 시리얼 번호",  required = true)
    private String usimSN;

    @ApiModelProperty(value = "약정 기간",  required = true)
    private ScmEnum.AggrementPeriod aggrementPeriod;

    @ApiModelProperty(value = "약정 유형",  required = true)
    private ScmEnum.AggrementType aggrementType;

    @ApiModelProperty(value = "할부 기간",  required = true)
    private ScmEnum.InstallmentsPeriod installmentsPeriod;

    @ApiModelProperty(value = "출고가",  required = true)
    private int outStockPrice;

    @ApiModelProperty(value = "공시 지원금",  required = true)
    private int pubNotiSupAmt;

    @ApiModelProperty(value = "추가 지원금")
    private int extraSupAmt;

    @ApiModelProperty(value = "프리할부")
    private int freeInstl;

    @ApiModelProperty(value = "기타 할인금")
    private int etcDiscountAmt;

    @ApiModelProperty(value = "실 판매가",  required = true)
    private int actuallSellPrice;

    @ApiModelProperty(value = "월 단말기 금액")
    private int monthDevicePrice;

    @ApiModelProperty(value = "월 기본료")
    private int monthBasicPrice;

    @ApiModelProperty(value = "월 납부금액")
    private int monthPaymentPrice;

    @ApiModelProperty(value = "입금 받을 금액")
    private int amtDeposited;

    @ApiModelProperty(value = "입금 여부",  required = true)
    private String depositYN;

    @ApiModelProperty(value = "요금제 아이디",  required = true)
    private Long callingPlanId;

    @ApiModelProperty(value = "개통점 아이디",  required = true)
    private Long openStoreId;

    @ApiModelProperty(value = "상품 아이디",  required = true)
    private Long goodsId;

    @ApiModelProperty(value = "부가서비스 리스트")
    private List<AppFormAddServiceDto> appFormAddServiceDtoList;



}
