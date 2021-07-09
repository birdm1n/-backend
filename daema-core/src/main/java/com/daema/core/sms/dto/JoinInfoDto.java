package com.daema.core.sms.dto;

import com.daema.core.commgmt.domain.Goods;
import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.sms.domain.*;
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
    private int amtDeposited;
    private String depositYN;
    private Long callingPlanId;
    private Long openStoreId;
    private Long goodsId;

   private List<Long> additionIds;
    private Long additionId;


}
