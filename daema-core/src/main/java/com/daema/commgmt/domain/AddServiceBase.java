package com.daema.commgmt.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AddServiceBase {

    @NotNull
    @Column(name = "telecom_code_id",  columnDefinition = "BIGINT unsigned comment '통신사 코드 아이디'")
    protected Long telecom;

    @NotBlank
    @Column(name = "add_svc_name", columnDefinition = "varchar(255) comment '부가 서비스 이름'")
    protected String addSvcName;

    @NotNull
    @Column(name = "add_svc_charge", columnDefinition = "INT comment '부가 서비스 요금'")
    protected int addSvcCharge;

    @Column(name = "add_svc_memo", columnDefinition = "varchar(255) comment '부가 서비스 메모'")
    protected String addSvcMemo;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    protected LocalDateTime regiDateTime;

    @Transient
    protected String telecomName;
}
