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
@EqualsAndHashCode(of="use_stplat_id")
@ToString
@Entity
@Table(name = "comtn_stplat_info")
public class ComtnStplatInfo {

    @Id
    @Column(name = "use_stplat_id")
    private String useStplatId;

    @Column(name = "use_stplat_nm")
    private String useStplatNm;

    @Column(name = "use_stplat_cn")
    private String useStplatCn;

    @Column(name = "info_provd_agre_cn")
    private String infoProvdAgreCn;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;


}
