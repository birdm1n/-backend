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
    @Column(name = "telecom", nullable = false)
    protected int telecom;

    @NotBlank
    @Column(name = "add_svc_name", length = 30, nullable = false)
    protected String addSvcName;

    @NotNull
    @Column(name = "add_svc_charge", nullable = false)
    protected int addSvcCharge;

    @Column(name = "add_svc_memo")
    protected String addSvcMemo;

    @Column(name = "regi_datetime")
    protected LocalDateTime regiDateTime;

    @Transient
    protected String telecomName;
}
