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
@EqualsAndHashCode(of="zip")
@ToString
@Entity
@Table(name = "comtc_zip")
public class ComtcZip {

    @Id
    @Column(name = "zip")
    private String zip;

    @Column(name = "sn")
    private double sn;

    @Column(name = "ctprvn_nm")
    private String ctprvnNm;

    @Column(name = "signgu_nm")
    private String signguNm;

    @Column(name = "emd_nm")
    private String emdNm;

    @Column(name = "li_buld_nm")
    private String liBuldNm;

    @Column(name = "lnbr_dong_ho")
    private String lnbrDongHo;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;


}
