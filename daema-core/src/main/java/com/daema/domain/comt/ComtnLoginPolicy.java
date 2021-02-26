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
@EqualsAndHashCode(of="emplyr_id")
@ToString
@Entity
@Table(name = "comtn_login_policy")
public class ComtnLoginPolicy {

    @Id
    @Column(name = "emplyr_id")
    private String emplyrId;

    @Column(name = "ip_info")
    private String ipInfo;

    @Column(name = "dplct_perm_at")
    private String dplctPermAt;

    @Column(name = "lmtt_at")
    private String lmttAt;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;


}
