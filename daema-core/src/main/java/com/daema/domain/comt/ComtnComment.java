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
@Table(name = "comtn_comment")
public class ComtnComment {

    @Id
    @Column(name = "ntt_id")
    private double nttId;

    @Column(name = "bbs_id")
    private String bbsId;

    @Column(name = "answer_no")
    private double answerNo;

    @Column(name = "wrter_id")
    private String wrterId;

    @Column(name = "wrter_nm")
    private String wrterNm;

    @Column(name = "answer")
    private String answer;

    @Column(name = "use_at")
    private String useAt;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;

    @Column(name = "password")
    private String password;


}
