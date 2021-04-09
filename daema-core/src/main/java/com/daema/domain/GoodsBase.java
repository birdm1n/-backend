package com.daema.domain;

import com.daema.domain.attr.NetworkAttribute;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class GoodsBase {

    @NotBlank
    @Column(name = "goods_name", length = 30, nullable = false)
    protected String goodsName;

    @NotBlank
    @Column(name = "model_name", length = 50, nullable = false)
    protected String modelName;

    @Column(name = "maker")
    protected int maker;

    @Embedded
    protected NetworkAttribute networkAttribute;

    @Column(name = "capacity", length = 8)
    protected String capacity;

    @Column(name = "regi_datetime")
    protected LocalDateTime regiDateTime;
}
