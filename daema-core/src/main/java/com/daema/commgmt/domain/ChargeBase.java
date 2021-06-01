package com.daema.commgmt.domain;

import com.daema.commgmt.domain.attr.NetworkAttribute;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class ChargeBase {

    @NotBlank
    @Column(name = "charge_name", columnDefinition = "varchar(255) comment '요금 명'")
    protected String chargeName;

    @NotBlank
    @Column(name = "charge_code",  columnDefinition = "varchar(255) comment '요금 코드'")
    protected String chargeCode;

    @Column(name = "category", columnDefinition = "varchar(255) comment '카테고리'")
    protected String category;

    @NotNull
    @Column(name = "charge_amt", columnDefinition = "INT comment '요금 금액'" )
    protected int chargeAmt;

    @Embedded
    protected NetworkAttribute networkAttribute;

    @Column(name = "voice_amt", columnDefinition = "varchar(255) comment '음성 금액'")
    protected String voiceAmt;

    @Column(name = "data_amt", columnDefinition = "varchar(255) comment '데이터 금액'")
    protected String dataAmt;

    @Column(name = "sms_amt", columnDefinition = "varchar(255) comment 'sms 금액'")
    protected String smsAmt;

    @Column(name = "video_amt", columnDefinition = "varchar(255) comment '영상 금액'")
    protected String videoAmt;

    @Column(name = "extra_voice_amt", columnDefinition = "varchar(255) comment '추가 음성 금액'")
    protected String extraVoiceAmt;

    @Column(name = "extra_data_amt", columnDefinition = "varchar(255) comment '추가 데이터 금액'")
    protected String extraDataAmt;

    @Column(name = "extra_sms_amt", columnDefinition = "varchar(255) comment '추가 sms 금액'")
    protected String extraSmsAmt;

    @Column(name = "extra_video_amt", columnDefinition = "varchar(255) comment '추가 영상 금액'")
    protected String extraVideoAmt;

    @Column(name = "charge_desc", columnDefinition = "varchar(255) comment '요금 설명'")
    protected String chargeDesc;

    @Column(name = "add_benefit", columnDefinition = "varchar(255) comment '부가 이익'")
    protected String addBenefit;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 날짜시간'")
    protected LocalDateTime regiDateTime;

    @Transient
    protected String makerName;

    @Transient
    protected String networkName;

    @Transient
    protected String telecomName;
}
