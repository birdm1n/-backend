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
@Table(name = "comtn_bbsmaster_optn")
public class ComtnBbsmasterOptn {

    @Id
    @Column(name = "bbs_id")
    private String bbsId;

    @Column(name = "answer_at")
    private String answerAt;

    @Column(name = "stsfdg_at")
    private String stsfdgAt;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;


}
