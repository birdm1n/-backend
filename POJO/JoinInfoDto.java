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
public class JoinInfoDto {

    @ApiModelProperty(value = "가입 아이디", example = "0")
    private Long joinInfoId;

    @ApiModelProperty(value = "실판매가", example = "0")
    private Integer actuallSellPrice;

    @ApiModelProperty(value = "약정 기간")
    private String aggremntPeriod;

    @ApiModelProperty(value = "약정 유형")
    private String aggrementType;

    @ApiModelProperty(value = "입금 받을 금액", example = "0")
    private Integer amtDeposited;

    @ApiModelProperty(value = "인증 번호", example = "0")
    private Integer certNum;

    @ApiModelProperty(value = "입금 여부")
    private String depositYn;

    @ApiModelProperty(value = "기타할인금", example = "0")
    private Integer etcDiscountAmt;

    @ApiModelProperty(value = "추가지원금", example = "0")
    private Integer extraSupAmt;

    @ApiModelProperty(value = "프리할부", example = "0")
    private Integer freeInstl;

    @ApiModelProperty(value = "할부 기간")
    private String insPeriod;

    @ApiModelProperty(value = "가입 유형")
    private String joinType;

    @ApiModelProperty(value = "월 기본료", example = "0")
    private Integer monthBasicPrice;

    @ApiModelProperty(value = "월 단말기 금액", example = "0")
    private Integer monthDevicePrice;

    @ApiModelProperty(value = "월 납부금액", example = "0")
    private Integer monthPaymentPrice;

    @ApiModelProperty(value = "개통 희망 번호")
    private String openHopePhoneNum;

    @ApiModelProperty(value = "개통 휴대폰 번호", example = "0")
    private Integer openPhoneNum;

    @ApiModelProperty(value = "출고가", example = "0")
    private Integer outStockPrice;

    @ApiModelProperty(value = "공시지원금", example = "0")
    private Integer pubNotiSupAmt;

    @ApiModelProperty(value = "유심")
    private String usim;

    @ApiModelProperty(value = "유심 일련번호")
    private String usimSn;

    @ApiModelProperty(value = "요금제", example = "0")
    private Long callingPlanId;

    @ApiModelProperty(value = "상품", example = "0")
    private Long goodsId;

    @ApiModelProperty(value = "개통점", example = "0")
    private Long openingStoreId;


}
