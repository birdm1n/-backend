package com.daema.domain;

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
    @Column(name = "support_amt", nullable = false)
    protected int supportAmt;

    @NotNull
    @Column(name = "release_amt", nullable = false)
    protected int releaseAmt;

    @NotNull
    @Column(name = "release_date")
    protected LocalDate releaseDate;

    @Column(name = "regi_datetime")
    protected LocalDateTime regiDateTime;
}
