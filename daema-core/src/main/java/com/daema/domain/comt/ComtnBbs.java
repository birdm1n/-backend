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
@EqualsAndHashCode(of="ntt_id")
@ToString
@Entity
@Table(name = "comtn_bbs")
public class ComtnBbs {

    @Id
    @Column(name = "ntt_id")
    private double nttId;

    @Column(name = "bbs_id")
    private String bbsId;

    @Column(name = "ntt_no")
    private double nttNo;

    @Column(name = "ntt_sj")
    private String nttSj;

    @Column(name = "ntt_cn")
    private String nttCn;

    @Column(name = "answer_at")
    private String answerAt;

    @Column(name = "parntsctt_no")
    private double parntscttNo;

    @Column(name = "answer_lc")
    private double answerLc;

    @Column(name = "sort_ordr")
    private double sortOrdr;

    @Column(name = "rdcnt")
    private double rdcnt;

    @Column(name = "use_at")
    private String useAt;

    @Column(name = "ntce_bgnde")
    private String ntceBgnde;

    @Column(name = "ntce_endde")
    private String ntceEndde;

    @Column(name = "ntcr_id")
    private String ntcrId;

    @Column(name = "ntcr_nm")
    private String ntcrNm;

    @Column(name = "password")
    private String password;

    @Column(name = "atch_file_id")
    private String atchFileId;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;


}
