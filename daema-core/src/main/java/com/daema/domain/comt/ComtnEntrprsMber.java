package com.daema.domain.comt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode(of="entrprs_mber_id")
@ToString
@Entity
@Table(name = "comtn_entrprs_mber")
public class ComtnEntrprsMber {

    @Id
    @Column(name = "entrprs_mber_id")
    private String entrprsMberId;

    @Column(name = "entrprs_se_code")
    private String entrprsSeCode;

    @Column(name = "bizrno")
    private String bizrno;

    @Column(name = "jurirno")
    private String jurirno;

    @Column(name = "cmpny_nm")
    private String cmpnyNm;

    @Column(name = "cxfc")
    private String cxfc;

    @Column(name = "zip")
    private String zip;

    @Column(name = "adres")
    private String adres;

    @Column(name = "entrprs_middle_telno")
    private String entrprsMiddleTelno;

    @Column(name = "fxnum")
    private String fxnum;

    @Column(name = "induty_code")
    private String indutyCode;

    @Column(name = "applcnt_nm")
    private String applcntNm;

    @Column(name = "applcnt_ihidnum")
    private String applcntIhidnum;

    @Column(name = "sbscrb_de")
    private java.sql.Timestamp sbscrbDe;

    @Column(name = "entrprs_mber_sttus")
    private String entrprsMberSttus;

    @Column(name = "entrprs_mber_password")
    private String entrprsMberPassword;

    @Column(name = "entrprs_mber_password_hint")
    private String entrprsMberPasswordHint;

    @Column(name = "entrprs_mber_password_cnsr")
    private String entrprsMberPasswordCnsr;

    @Column(name = "detail_adres")
    private String detailAdres;

    @Column(name = "entrprs_end_telno")
    private String entrprsEndTelno;

    @Column(name = "area_no")
    private String areaNo;

    @Column(name = "applcnt_email_adres")
    private String applcntEmailAdres;

    @Column(name = "esntl_id")
    private String esntlId;


}
