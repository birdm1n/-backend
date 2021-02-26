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
@EqualsAndHashCode(of="rdmn_code")
@ToString
@Entity
@Table(name = "comtr_dnm_adrzip")
public class ComtrDnmAdrzip {

    @Id
    @Column(name = "rdmn_code")
    private String rdmnCode;

    @Column(name = "sn")
    private double sn;

    @Column(name = "ctprvn_nm")
    private String ctprvnNm;

    @Column(name = "signgu_nm")
    private String signguNm;

    @Column(name = "rdmn")
    private String rdmn;

    @Column(name = "bdnbr_mnnm")
    private String bdnbrMnnm;

    @Column(name = "bdnbr_slno")
    private String bdnbrSlno;

    @Column(name = "buld_nm")
    private String buldNm;

    @Column(name = "detail_buld_nm")
    private String detailBuldNm;

    @Column(name = "zip")
    private String zip;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;


}
