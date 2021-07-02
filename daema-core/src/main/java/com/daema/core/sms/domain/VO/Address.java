package com.daema.core.sms.domain.VO;

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
public class Address {
    @Column(name = "city", columnDefinition = "varchar(255) comment '주소'") // 매핑할 컬럼 정의 가능
    private String city;
    @Column(name = "street", columnDefinition = "varchar(255) comment '상세주소'") // 매핑할 컬럼 정의 가능
    private String street;
    @Column(name = "zipcode", columnDefinition = "varchar(255) comment '우편번호'") // 매핑할 컬럼 정의 가능
    private String zipcode;




}
