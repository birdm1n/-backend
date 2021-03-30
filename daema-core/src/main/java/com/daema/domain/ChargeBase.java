package com.daema.domain;

import com.daema.domain.attr.NetworkAttribute;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class ChargeBase {

    @NotBlank
    @Column(name = "charge_name", length = 30, nullable = false)
    protected String chargeName;

    @NotBlank
    @Column(name = "category", length = 100, nullable = false)
    protected String category;

    @NotNull
    @Column(name = "charge_amt", nullable = false)
    protected int chargeAmt;

    @Embedded
    protected NetworkAttribute networkAttribute;

    @Column(name = "regi_datetime")
    protected LocalDateTime regiDateTime;
}
