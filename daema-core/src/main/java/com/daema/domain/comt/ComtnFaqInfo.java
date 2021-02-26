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
@EqualsAndHashCode(of="faq_id")
@ToString
@Entity
@Table(name = "comtn_faq_info")
public class ComtnFaqInfo {

    @Id
    @Column(name = "faq_id")
    private String faqId;

    @Column(name = "qestn_sj")
    private String qestnSj;

    @Column(name = "qestn_cn")
    private String qestnCn;

    @Column(name = "answer_cn")
    private String answerCn;

    @Column(name = "rdcnt")
    private double rdcnt;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;

    @Column(name = "atch_file_id")
    private String atchFileId;

    @Column(name = "qna_process_sttus_code")
    private String qnaProcessSttusCode;


}
