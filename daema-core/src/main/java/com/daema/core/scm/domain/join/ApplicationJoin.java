package com.daema.core.scm.domain.join;

import com.daema.core.base.domain.common.BaseUserInfoEntity;
import com.daema.core.commgmt.domain.Goods;
import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.scm.domain.application.Application;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.ApplicationJoinDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applId")
@ToString(exclude = {"application", "goodsId", "goodsOptionId", "applicationAddServiceList", "charge"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_join", comment = "신청서 가입")
public class ApplicationJoin extends BaseUserInfoEntity {


    @Id
    private Long applId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appl_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 아이디'")
    @MapsId
    private Application application;

    @Column(name = "open_phone_num", columnDefinition = "INT comment '개통 휴대폰 번호'")
    private int openPhoneNo;

    @Column(name = "open_hope_phone_num", columnDefinition = "varchar(255) comment '개통 희망 번호'")
    private String openHopePhoneNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "join_type", columnDefinition = "varchar(255) comment '가입 유형'")
    private ScmEnum.JoinType joinType;

    @Column(name = "cert_num", columnDefinition = "INT comment '인증 번호'")
    private int certNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "usim", columnDefinition = "varchar(255) comment '유심'")
    private ScmEnum.Usim usim;

    @Column(name = "usim_sn", columnDefinition = "varchar(255) comment '유심 일련번호'")
    private String usimSN;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggremnt_period", columnDefinition = "varchar(255) comment '약정 기간'")
    private ScmEnum.AggrementPeriod aggrementPeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggrement_type", columnDefinition = "varchar(255) comment '약정 유형'")
    private ScmEnum.AggrementType aggrementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ins_period", columnDefinition = "varchar(255) comment '할부 기간'")
    private ScmEnum.InstallmentsPeriod installmentsPeriod;

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

    @Column(name = "month_basic_price", columnDefinition = "INT comment '월 기본료'")
    private int monthBasicPrice;

    @Column(name = "month_payment_price", columnDefinition = "INT comment '월 납부금액'")
    private int monthPaymentPrice;

    @Column(name = "amt_deposited", columnDefinition = "INT comment '입금 받을 금액'")
    private int amtDeposited;

    @Column(name = "deposit_yn", columnDefinition = "char(1) comment '입금 여부'")
    private String depositYN;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calling_plan_id", columnDefinition = "BIGINT UNSIGNED comment '요금제'")
    private CallingPlan callingPlan;

    @OneToMany(mappedBy = "applicationJoin", cascade = CascadeType.ALL)
    private List<ApplicationJoinAddService> AddServiceList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_store_id", columnDefinition = "BIGINT UNSIGNED comment '개통점'")
    private OpenStore openStore;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id", columnDefinition = "BIGINT UNSIGNED comment '상품'")
    private Goods goods;


    public static ApplicationJoin create(Application application, ApplicationJoinDto joinInfoDto) {

        ApplicationJoin applicationJoin = build(application, joinInfoDto);
        applicationJoin.AddServiceList = ApplicationJoinAddService.createList(applicationJoin, joinInfoDto.getApplicationAddServiceDtoList());

        return applicationJoin;
    }



    public static ApplicationJoinDto from(ApplicationJoin applicationJoin) {
        return ApplicationJoinDto.builder()
                .openPhoneNo(applicationJoin.getOpenPhoneNo())
                .openHopePhoneNo(applicationJoin.getOpenHopePhoneNo())
                .joinType(applicationJoin.getJoinType())
                .certNo(applicationJoin.getCertNo())
                .usim(applicationJoin.getUsim())
                .usimSN(applicationJoin.getUsimSN())
                .aggrementPeriod(applicationJoin.getAggrementPeriod())
                .aggrementType(applicationJoin.getAggrementType())
                .installmentsPeriod(applicationJoin.getInstallmentsPeriod())
                .outStockPrice(applicationJoin.getOutStockPrice())
                .pubNotiSupAmt(applicationJoin.getPubNotiSupAmt())
                .extraSupAmt(applicationJoin.getExtraSupAmt())
                .freeInstl(applicationJoin.getFreeInstl())
                .actuallSellPrice(applicationJoin.getActuallSellPrice())
                .monthDevicePrice(applicationJoin.getMonthDevicePrice())
                .monthBasicPrice(applicationJoin.getMonthBasicPrice())
                .monthPaymentPrice(applicationJoin.getMonthPaymentPrice())
                .amtDeposited(applicationJoin.getAmtDeposited())
                .depositYN(applicationJoin.getDepositYN())
                .callingPlanId(applicationJoin.getCallingPlan().getCallingPlanId())
                .openStoreId(applicationJoin.getOpenStore().getOpenStoreId())
                .goodsId(applicationJoin.getGoods().getGoodsId())
                .build();
    }



    public static ApplicationJoin build(Application application, ApplicationJoinDto joinInfoDto) {
        return ApplicationJoin.builder()
                .application(application)
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
                .amtDeposited(joinInfoDto.getAmtDeposited())
                .depositYN(joinInfoDto.getDepositYN())
                .callingPlan(CallingPlan.builder()
                        .callingPlanId(joinInfoDto.getCallingPlanId()).build())
                .openStore(OpenStore.builder()
                        .openStoreId(joinInfoDto.getOpenStoreId())
                        .build())

                .goods(Goods.builder()
                        .goodsId(joinInfoDto.getGoodsId())

                        .build()
                )

                .build()

                ;
    }


    public static void update(ApplicationJoin applicationJoin, ApplicationJoinDto joinInfoDto) {

        applicationJoin.setOpenHopePhoneNo(joinInfoDto.getOpenHopePhoneNo());
        applicationJoin.setOpenPhoneNo(joinInfoDto.getOpenPhoneNo());
        applicationJoin.setJoinType(joinInfoDto.getJoinType());
        applicationJoin.setCertNo(joinInfoDto.getCertNo());
        applicationJoin.setUsim(joinInfoDto.getUsim());
        applicationJoin.setUsimSN(joinInfoDto.getUsimSN());
        applicationJoin.setAggrementPeriod(joinInfoDto.getAggrementPeriod());
        applicationJoin.setAggrementType(joinInfoDto.getAggrementType());
        applicationJoin.setInstallmentsPeriod(joinInfoDto.getInstallmentsPeriod());
        applicationJoin.setOutStockPrice(joinInfoDto.getOutStockPrice());
        applicationJoin.setPubNotiSupAmt(joinInfoDto.getPubNotiSupAmt());
        applicationJoin.setExtraSupAmt(joinInfoDto.getExtraSupAmt());
        applicationJoin.setFreeInstl(joinInfoDto.getFreeInstl());
        applicationJoin.setActuallSellPrice(joinInfoDto.getActuallSellPrice());
        applicationJoin.setMonthDevicePrice(joinInfoDto.getMonthDevicePrice());
        applicationJoin.setMonthBasicPrice(joinInfoDto.getMonthBasicPrice());
        applicationJoin.setMonthPaymentPrice(joinInfoDto.getMonthPaymentPrice());
        applicationJoin.setDepositYN(joinInfoDto.getDepositYN());
        applicationJoin.setAmtDeposited(joinInfoDto.getAmtDeposited());
        applicationJoin.setCallingPlan(CallingPlan.builder()
                .callingPlanId(joinInfoDto.getCallingPlanId()).build());
        applicationJoin.setOpenStore(OpenStore.builder()
                .openStoreId(joinInfoDto.getOpenStoreId())
                .build());
        applicationJoin.setGoods(Goods.builder()
                .goodsId(joinInfoDto.getGoodsId())
                .build());


    }
}


