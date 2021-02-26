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
@EqualsAndHashCode(of="restde_no")
@ToString
@Entity
@Table(name = "comtn_rest_de")
public class ComtnRestDe {

    @Id
    @Column(name = "restde_no")
    private double restdeNo;

    @Column(name = "restde")
    private String restde;

    @Column(name = "restde_nm")
    private String restdeNm;

    @Column(name = "restde_dc")
    private String restdeDc;

    @Column(name = "restde_se_code")
    private String restdeSeCode;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;


}
