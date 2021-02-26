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
@EqualsAndHashCode(of="qa_id")
@ToString
@Entity
@Table(name = "comtn_qa_info")
public class ComtnQaInfo {

    @Id
    @Column(name = "qa_id")
    private String qaId;

    @Column(name = "qestn_sj")
    private String qestnSj;

    @Column(name = "qestn_cn")
    private String qestnCn;

    @Column(name = "writng_de")
    private String writngDe;

    @Column(name = "rdcnt")
    private double rdcnt;

    @Column(name = "email_adres")
    private String emailAdres;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;

    @Column(name = "qna_process_sttus_code")
    private String qnaProcessSttusCode;

    @Column(name = "wrter_nm")
    private String wrterNm;

    @Column(name = "answer_cn")
    private String answerCn;

    @Column(name = "writng_password")
    private String writngPassword;

    @Column(name = "answer_de")
    private String answerDe;

    @Column(name = "email_answer_at")
    private String emailAnswerAt;

    @Column(name = "area_no")
    private String areaNo;

    @Column(name = "middle_telno")
    private String middleTelno;

    @Column(name = "end_telno")
    private String endTelno;


}
