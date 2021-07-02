package com.daema.core.sms.domain;

import com.daema.core.commgmt.domain.Goods;
import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of="joinInfoId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "join_info", comment = "가입 정보")
public class JoinInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_info_id", columnDefinition = "BIGINT UNSIGNED comment '가입 아이디'")
    private Long joinInfoId;

    @Column(name= "open_phone_num", columnDefinition = "INT comment '개통 휴대폰 번호'")
    private int openPhoneNo;

    @Column(name = "open_hope_phone_num", columnDefinition = "varchar(255) comment '개통 희망 번호'")
    private String openHopePhoneNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "join_type", columnDefinition = "varchar(255) comment '가입 유형'")
    private SmsEnum.JoinType joinType;

    @Column(name = "cert_num", columnDefinition = "INT comment '인증 번호'")
    private int certNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "usim", columnDefinition = "varchar(255) comment '유심'")
    private SmsEnum.Usim usim;

    @Column(name = "usim_sn", columnDefinition = "varchar(255) comment '유심 일련번호'")
    private String usimSN;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggremnt_period", columnDefinition = "varchar(255) comment '약정 기간'")
    private SmsEnum.AggrementPeriod aggrementPeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggrement_type", columnDefinition = "varchar(255) comment '약정 유형'")
    private SmsEnum.AggrementType aggrementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ins_period", columnDefinition = "varchar(255) comment '할부 기간'")
    private SmsEnum.InstallmentsPeriod installmentsPeriod;

    @Column(name = "outStock_price", columnDefinition = "INT comment '출고가'")
    private int outStockPrice;

    @Column(name = "pub_noti_sup_amt", columnDefinition = "INT comment '공시지원금'")
    private int pubNotiSupAmt;

    @Column(name = "extra_sup_amt", columnDefinition = "INT comment '추가지원금'")
    private int extraSupAmt;

    @Column(name = "free_instl", columnDefinition = "INT comment '프리할부'")
    private int freeInstl;

    @Column(name = "etc_discount_amt", columnDefinition = "INT comment '기타할인금'")
    private int etcDiscountAmt;

    @Column(name = "actuall_sell_price", columnDefinition = "INT comment '실판매가'")
    private int actuallSellPrice;

    @Column(name = "month_device_price", columnDefinition = "INT comment '월 단말기 금액'")
    private int monthDevicePrice;

    @Column(name = "month_basic_price", columnDefinition = "INT comment '월 기본료")
    private int monthBasicPrice;

    @Column(name = "month_payment_price", columnDefinition = "INT comment '월 납부금액")
    private int monthPaymentPrice;

    @Column(name = "amount_to_be_deposited", columnDefinition = "INT comment '입금 받을 금액")
    private int amountToBeDeposited;

    @Column(name = "deposit_yn", columnDefinition = "char(1) comment '입금 여부'")
    private String depositYN;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calling_plan_id", columnDefinition = "BIGINT UNSIGNED comment '요금제'")
    private CallingPlan callingPlan;

    @OneToMany(mappedBy = "joinInfo")
    private List<Addition> addition = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_store_id", columnDefinition = "BIGINT UNSIGNED comment '개통점'")
    private OpenStore openStore;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id", columnDefinition = "BIGINT UNSIGNED comment '상품'")
    private Goods goods;
}
