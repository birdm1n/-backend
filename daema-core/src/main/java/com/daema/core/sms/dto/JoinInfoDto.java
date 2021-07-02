package com.daema.core.sms.dto;

import com.daema.core.commgmt.domain.Goods;
import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.sms.domain.Addition;
import com.daema.core.sms.domain.CallingPlan;
import com.daema.core.sms.domain.Giro;
import com.daema.core.sms.domain.JoinInfo;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinInfoDto {

    private Long joinInfoId;

    private int openPhoneNo;
    private String openHopePhoneNo;
    private SmsEnum.JoinType joinType;
    private int certNo;
    private SmsEnum.Usim usim;
    private String usimSN;
    private SmsEnum.AggrementPeriod aggrementPeriod;
    private SmsEnum.AggrementType aggrementType;
    private SmsEnum.InstallmentsPeriod installmentsPeriod;
    private int outStockPrice;
    private int pubNotiSupAmt;
    private int extraSupAmt;
    private int freeInstl;
    private int etcDiscountAmt;
    private int actuallSellPrice;
    private int monthDevicePrice;
    private int monthBasicPrice;
    private int monthPaymentPrice;
    private int amountToBeDeposited;
    private String depositYN;
    private CallingPlan callingPlan;
    private List<Addition> addition = new ArrayList<>();
    private OpenStore openStore;
    private Goods goods;

    public static JoinInfoDto from(JoinInfo joinInfo) {
        return JoinInfoDto.builder()
                .openPhoneNo(joinInfo.getOpenPhoneNo())
                .openHopePhoneNo(joinInfo.getOpenHopePhoneNo())
                .joinType(joinInfo.getJoinType())
                .certNo(joinInfo.getCertNo())
                .usim(joinInfo.getUsim())
                .usimSN(joinInfo.getUsimSN())
                .aggrementPeriod(joinInfo.getAggrementPeriod())
                .aggrementType(joinInfo.getAggrementType())
                .installmentsPeriod(joinInfo.getInstallmentsPeriod())
                .outStockPrice(joinInfo.getOutStockPrice())
                .pubNotiSupAmt(joinInfo.getPubNotiSupAmt())
                .extraSupAmt(joinInfo.getExtraSupAmt())
                .freeInstl(joinInfo.getFreeInstl())
                .actuallSellPrice(joinInfo.getActuallSellPrice())
                .monthDevicePrice(joinInfo.getMonthDevicePrice())
                .monthBasicPrice(joinInfo.getMonthBasicPrice())
                .monthPaymentPrice(joinInfo.getMonthPaymentPrice())
                .amountToBeDeposited(joinInfo.getAmountToBeDeposited())
                .depositYN(joinInfo.getDepositYN())
                .callingPlan(joinInfo.getCallingPlan())
                .addition(joinInfo.getAddition())
                .openStore(joinInfo.getOpenStore())
                .goods(joinInfo.getGoods())
                .build();
    }




    public static JoinInfo toEntity(JoinInfoDto joinInfoDto) {
        return JoinInfo.builder()
                .openPhoneNo(joinInfoDto.getOpenPhoneNo())
                .openHopePhoneNo(joinInfoDto.getOpenHopePhoneNo())
                .joinType(joinInfoDto.getJoinType())
                .certNo(joinInfoDto.getCertNo())
                .usim(joinInfoDto.getUsim())
                .usimSN(joinInfoDto.getUsimSN())
                .aggrementPeriod(joinInfoDto.getAggrementPeriod())
                .aggrementType(joinInfoDto.getAggrementType())
                .installmentsPeriod(joinInfoDto.getInstallmentsPeriod())
                .outStockPrice(joinInfoDto.getOutStockPrice())
                .pubNotiSupAmt(joinInfoDto.getPubNotiSupAmt())
                .extraSupAmt(joinInfoDto.getExtraSupAmt())
                .freeInstl(joinInfoDto.getFreeInstl())
                .actuallSellPrice(joinInfoDto.getActuallSellPrice())
                .monthDevicePrice(joinInfoDto.getMonthDevicePrice())
                .monthBasicPrice(joinInfoDto.getMonthBasicPrice())
                .monthPaymentPrice(joinInfoDto.getMonthPaymentPrice())
                .amountToBeDeposited(joinInfoDto.getAmountToBeDeposited())
                .depositYN(joinInfoDto.getDepositYN())
                .callingPlan(joinInfoDto.getCallingPlan())
                .addition(joinInfoDto.getAddition())
                .openStore(joinInfoDto.getOpenStore())
                .goods(joinInfoDto.getGoods())
                .build();
    }
}
