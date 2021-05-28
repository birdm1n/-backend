package com.daema.commgmt.domain;

import com.daema.commgmt.domain.attr.NetworkAttribute;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter

@MappedSuperclass
public abstract class GoodsBase {

    @NotBlank
    @Column(name = "goods_name", length = 30, columnDefinition = "varchar(255)  is not null comment '상품 명'")
    protected String goodsName;

    @NotBlank
    @Column(name = "model_name", length = 50, columnDefinition = "varchar(255)  is not null comment '모델 명'")
    protected String modelName;

    @Column(name = "maker_code_id", columnDefinition = "INT comment '제조'")
    protected int maker;

    @Embedded
    protected NetworkAttribute networkAttribute;


    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 날짜시간'")
    protected LocalDateTime regiDateTime;

    @Transient
    protected String makerName;

    @Transient
    protected String networkName;

    @Transient
    protected String telecomName;
}
