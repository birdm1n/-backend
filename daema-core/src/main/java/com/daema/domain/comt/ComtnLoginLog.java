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
@EqualsAndHashCode(of="log_id")
@ToString
@Entity
@Table(name = "comtn_login_log")
public class ComtnLoginLog {

    @Id
    @Column(name = "log_id")
    private String logId;

    @Column(name = "conect_id")
    private String conectId;

    @Column(name = "conect_ip")
    private String conectIp;

    @Column(name = "conect_mthd")
    private String conectMthd;

    @Column(name = "error_occrrnc_at")
    private String errorOccrrncAt;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "creat_dt")
    private java.sql.Timestamp creatDt;


}
