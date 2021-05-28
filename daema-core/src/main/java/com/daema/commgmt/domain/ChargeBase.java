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
    @Column(name = "charge_name", nullable = false)
    protected String chargeName;

    @NotBlank
    @Column(name = "charge_code", length = 20, nullable = false)
    protected String chargeCode;

    @Column(name = "category", length = 100)
    protected String category;

    @NotNull
    @Column(name = "charge_amt", nullable = false)
    protected int chargeAmt;

    @Embedded
    protected NetworkAttribute networkAttribute;

    @Column(name = "voice_amt")
    protected String voiceAmt;

    @Column(name = "data_amt")
    protected String dataAmt;

    @Column(name = "sms_amt")
    protected String smsAmt;

    @Column(name = "video_amt")
    protected String videoAmt;

    @Column(name = "extra_data_amt")
    protected String extraVoiceAmt;

    @Column(name = "extra_data_amt")
    protected String extraDataAmt;

    @Column(name = "extra_sms_amt")
    protected String extraSmsAmt;

    @Column(name = "extra_video_amt")
    protected String extraVideoAmt;

    @Column(name = "charge_desc")
    protected String chargeDesc;

    @Column(name = "add_benefit")
    protected String addBenefit;

    @Column(name = "regi_datetime")
    protected LocalDateTime regiDateTime;

    @Transient
    protected String makerName;

    @Transient
    protected String networkName;

    @Transient
    protected String telecomName;
}
