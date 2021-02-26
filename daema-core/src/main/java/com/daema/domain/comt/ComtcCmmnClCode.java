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
@EqualsAndHashCode(of="cl_code")
@ToString
@Entity
@Table(name = "comtc_cmmn_cl_code")
public class ComtcCmmnClCode {

    @Id
    @Column(name = "cl_code")
    private String clCode;

    @Column(name = "cl_code_nm")
    private String clCodeNm;

    @Column(name = "cl_code_dc")
    private String clCodeDc;

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
