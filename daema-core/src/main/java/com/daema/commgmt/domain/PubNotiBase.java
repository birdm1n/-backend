package com.daema.commgmt.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class PubNotiBase {

    @NotNull
    @Column(name = "support_amt", columnDefinition = "int comment '지원 금액'")
    protected int supportAmt;

    @NotNull
    @Column(name = "release_amt", columnDefinition = "int comment '출고가 금액'")
    protected int releaseAmt;

    @NotNull
    @Column(name = "release_date", columnDefinition = "DATETIME(6) comment '출고가 일자'")
    protected LocalDate releaseDate;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    protected LocalDateTime regiDateTime;
}
