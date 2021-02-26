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
@EqualsAndHashCode(of="word_id")
@ToString
@Entity
@Table(name = "comtn_word_dicary_info")
public class ComtnWordDicaryInfo {

    @Id
    @Column(name = "word_id")
    private String wordId;

    @Column(name = "word_nm")
    private String wordNm;

    @Column(name = "eng_nm")
    private String engNm;

    @Column(name = "word_dc")
    private String wordDc;

    @Column(name = "synonm")
    private String synonm;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;


}
