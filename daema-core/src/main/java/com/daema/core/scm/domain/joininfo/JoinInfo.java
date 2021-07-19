package com.daema.core.scm.domain.joininfo;

import com.daema.core.commgmt.domain.Goods;
import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.scm.domain.appform.AppForm;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.JoinInfoDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "joinInfoId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "join_info", comment = "가입 정보")
public class JoinInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_info_id", columnDefinition = "BIGINT UNSIGNED comment '가입 아이디'")
    private Long joinInfoId;

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

    @OneToMany(mappedBy = "joinInfo", cascade = CascadeType.ALL)
    private List<JoinAddition> joinAdditionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_store_id", columnDefinition = "BIGINT UNSIGNED comment '개통점'")
    private OpenStore openStore;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id", columnDefinition = "BIGINT UNSIGNED comment '상품'")
    private Goods goods;

    @OneToOne(mappedBy = "joinInfo")
    private AppForm appform;



    public void createJoinAdditionList(List<Long> additionIds) {
        List<JoinAddition> QjoinAdditionList = new ArrayList<>();
        for (Long additionId : additionIds)
        {
            QjoinAdditionList.add(JoinAddition.builder()
                    .joinInfo(JoinInfo.builder()
                            .joinInfoId(this.getJoinInfoId())
                            .build())
                    .addition(Addition.builder()
                            .additionId(additionId)
                            .build())
                    .build());
        }
        joinAdditionList = QjoinAdditionList;
    }



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
                .amtDeposited(joinInfo.getAmtDeposited())
                .depositYN(joinInfo.getDepositYN())
                .callingPlanId(joinInfo.getCallingPlan().getCallingPlanId())
                .openStoreId(joinInfo.getOpenStore().getOpenStoreId())
                .goodsId(joinInfo.getGoods().getGoodsId())
                .build();
    }



    public static JoinInfo create(JoinInfoDto joinInfoDto) {
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


    public static void update(JoinInfo joinInfo, JoinInfoDto joinInfoDto) {

        joinInfo.setOpenHopePhoneNo(joinInfoDto.getOpenHopePhoneNo());
        joinInfo.setOpenPhoneNo(joinInfoDto.getOpenPhoneNo());
        joinInfo.setJoinType(joinInfoDto.getJoinType());
        joinInfo.setCertNo(joinInfoDto.getCertNo());
        joinInfo.setUsim(joinInfoDto.getUsim());
        joinInfo.setUsimSN(joinInfoDto.getUsimSN());
        joinInfo.setAggrementPeriod(joinInfoDto.getAggrementPeriod());
        joinInfo.setAggrementType(joinInfoDto.getAggrementType());
        joinInfo.setInstallmentsPeriod(joinInfoDto.getInstallmentsPeriod());
        joinInfo.setOutStockPrice(joinInfoDto.getOutStockPrice());
        joinInfo.setPubNotiSupAmt(joinInfoDto.getPubNotiSupAmt());
        joinInfo.setExtraSupAmt(joinInfoDto.getExtraSupAmt());
        joinInfo.setFreeInstl(joinInfoDto.getFreeInstl());
        joinInfo.setActuallSellPrice(joinInfoDto.getActuallSellPrice());
        joinInfo.setMonthDevicePrice(joinInfoDto.getMonthDevicePrice());
        joinInfo.setMonthBasicPrice(joinInfoDto.getMonthBasicPrice());
        joinInfo.setMonthPaymentPrice(joinInfoDto.getMonthPaymentPrice());
        joinInfo.setDepositYN(joinInfoDto.getDepositYN());
        joinInfo.setAmtDeposited(joinInfoDto.getAmtDeposited());
        joinInfo.setCallingPlan(CallingPlan.builder()
                .callingPlanId(joinInfoDto.getCallingPlanId()).build());
        joinInfo.setOpenStore(OpenStore.builder()
                .openStoreId(joinInfoDto.getOpenStoreId())
                .build());
        joinInfo.setGoods(Goods.builder()
                .goodsId(joinInfoDto.getGoodsId())
                .build());


    }
}


