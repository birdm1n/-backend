package com.daema.commgmt.domain.attr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NetworkAttribute {

    @Column(name = "telecom_code_id", columnDefinition = "BIGINT unsigned comment '통신사 코드 아이디'")
    public Long telecom;

    @Column(name = "network_code_id", columnDefinition = "BIGINT unsigned comment '통신망 코드 아이디'")
    public Long network;
}
