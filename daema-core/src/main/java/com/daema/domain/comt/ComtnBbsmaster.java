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
@EqualsAndHashCode(of="bbs_id")
@ToString
@Entity
@Table(name = "comtn_bbsmaster")
public class ComtnBbsmaster {

    @Id
    @Column(name = "bbs_id")
    private String bbsId;

    @Column(name = "bbs_nm")
    private String bbsNm;

    @Column(name = "bbs_intrcn")
    private String bbsIntrcn;

    @Column(name = "bbs_ty_code")
    private String bbsTyCode;

    @Column(name = "bbs_attrb_code")
    private String bbsAttrbCode;

    @Column(name = "reply_posbl_at")
    private String replyPosblAt;

    @Column(name = "file_atch_posbl_at")
    private String fileAtchPosblAt;

    @Column(name = "atch_posbl_file_number")
    private double atchPosblFileNumber;

    @Column(name = "atch_posbl_file_size")
    private double atchPosblFileSize;

    @Column(name = "use_at")
    private String useAt;

    @Column(name = "tmplat_id")
    private String tmplatId;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;


}
