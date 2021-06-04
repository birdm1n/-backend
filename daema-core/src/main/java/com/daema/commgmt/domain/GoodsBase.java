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
    @Column(name = "goods_name", columnDefinition = "varchar(255) comment '상품 이름'")
    protected String goodsName;

    @NotBlank
    @Column(name = "model_name", columnDefinition = "varchar(255) comment '모델 이름'")
    protected String modelName;

    @Column(name = "maker_code_id", columnDefinition = "BIGINT unsigned comment '제조사 코드 아이디'")
    protected Long maker;

    @Embedded
    protected NetworkAttribute networkAttribute;


    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    protected LocalDateTime regiDateTime;

    @Transient
    protected String makerName;

    @Transient
    protected String networkName;

    @Transient
    protected String telecomName;
}
