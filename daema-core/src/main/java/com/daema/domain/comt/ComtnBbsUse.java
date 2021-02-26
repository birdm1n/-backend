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
@Table(name = "comtn_bbs_use")
public class ComtnBbsUse {

    @Id
    @Column(name = "bbs_id")
    private String bbsId;

    @Column(name = "trget_id")
    private String trgetId;

    @Column(name = "use_at")
    private String useAt;

    @Column(name = "regist_se_code")
    private String registSeCode;

    @Column(name = "frst_regist_pnttm")
    private java.sql.Timestamp frstRegistPnttm;

    @Column(name = "frst_register_id")
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private java.sql.Timestamp lastUpdtPnttm;

    @Column(name = "last_updusr_id")
    private String lastUpdusrId;


}
