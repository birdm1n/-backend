package com.daema.core.sms.dto.request;

import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FillOutApplicationRequestDto {
    //Id
    private Long accountId;
    private Long additionId;
    private Long callingPlanId;
    private Long cardId;
    private Long courtProctorId;
    private Long customerId;
    private Long giroId;
    private Long insuranceId;
    private Long joinInfoId;
    private Long paymentId;
    private Long openStoreId;
    private Long goodsId;


    //accountDto
    private String bank;
    private int accountNo;
    private String accountHolder;
    private int dateOfBirth;
    private String relation;

    //addtionDto
    private SmsEnum.AdditionCategory additionCategory;
    private String productName;
    private int charge;

    //callingplanDto
    private String callingPlanName;

    //cardDto
    private String cardInfo;
    private int cardNo;
    private String cardHolder;
    private int residentRegistrationNo;
    private int cardExpiryDate;

    //CourProctorDto
    private String courProctorName;
    private String courProctoremail;
    private int courProctorRegistNo;
    private int courProctorPhoneNo;
    private String relationship;

    //CustomerDto
    private int cusRegistNo;
    private int chargeReduct;
    private int cusPhoneNo;
    private int cusEmgPhone;
    private String cusEmail;
    private String cusCity;
    private String cusStreet;
    private String cusZipcode;
    private SmsEnum.CustomerType customerType;
    private SmsEnum.LicenseType cusLicenseType;
    private int cusIssueDate;
    private int cusExpiredDate;
    private int cusForeignRegiNo;
    private SmsEnum.Area cusArea;
    private int cusLicenseNo;
    private int cusStayCode;
    private String cusCountry;

    //GiroDto
    private String giroPaymentCity;
    private String giroPaymentStreet;
    private String giroPaymentZipcode;


    //inusranceDto

    //joinInfoDto


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

    //paymentDto

    private SmsEnum.PaymentWay paymentWay;
}
