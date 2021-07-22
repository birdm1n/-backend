package com.daema.core.scm.domain.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Address {
    @Column(name = "zip_code", columnDefinition = "varchar(255) comment '우편 코드'")
    private String zipCode;

    @Column(name = "addr", columnDefinition = "varchar(255) comment '주소'")
    private String addr;

    @Column(name = "addr_detail", columnDefinition = "varchar(255) comment '주소 상세'")
    private String addrDetail;

    private Address(String zipCode, String addr, String addrDetail) {
        this.zipCode = zipCode;
        this.addr = addr;
        this.addrDetail = addrDetail;

    }
    public static Address create(String zipCode, String addr, String addrDetail) {
        return new Address(zipCode, addr, addrDetail);
    }
}

