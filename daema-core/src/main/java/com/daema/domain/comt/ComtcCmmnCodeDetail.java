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
@EqualsAndHashCode(of="code_id")
@ToString
@Entity
@Table(name = "comtc_cmmn_code_detail")
public class ComtcCmmnCodeDetail {

    @Id
    @Column(name = "code_id")
    private String codeId;

    @Column(name = "code")
    private String code;

    @Column(name = "code_nm")
    private String codeNm;

    @Column(name = "code_dc")
    private String codeDc;

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


}
