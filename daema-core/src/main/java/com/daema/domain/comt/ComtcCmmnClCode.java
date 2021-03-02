package com.daema.domain.comt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="clCode")
@ToString
@Entity
@Table(name = "comtc_cmmn_cl_code")
public class ComtcCmmnClCode {

    @Id
    @Column(name = "cl_code", length = 3, nullable = false)
    private String clCode;

    @Column(name = "cl_code_nm", length = 60)
    private String clCodeNm;

    @Column(name = "cl_code_dc", length = 200)
    private String clCodeDc;

    @Column(name = "use_at", length = 1)
    private String useAt;

    @Column(name = "frst_regist_pnttm")
    private LocalDateTime frstRegistPnttm;

    @Column(name = "frst_register_id", length = 20)
    private String frstRegisterId;

    @Column(name = "last_updt_pnttm")
    private LocalDateTime lastUpdtPnttm;

    @Column(name = "last_updusr_id", length = 20)
    private String lastUpdusrId;


}
