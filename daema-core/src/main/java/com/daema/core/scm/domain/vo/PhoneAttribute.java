package com.daema.core.scm.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PhoneAttribute {

    @Column(name = "phone", columnDefinition = "varchar(255) comment '연락처'")
    private String phone;

    @Column(name = "phone1", columnDefinition = "varchar(255) comment '연락처1'")
    private String phone1;

    @Column(name = "phone2", columnDefinition = "varchar(255) comment '연락처2'")
    private String phone2;

    @Column(name = "phone3", columnDefinition = "varchar(255) comment '연락처3'")
    private String phone3;

    private PhoneAttribute(String phone1, String phone2, String phone3) {
        if (StringUtils.hasText(phone1)
                && StringUtils.hasText(phone2)
                && StringUtils.hasText(phone3)) {
            this.phone = phone1.concat(phone2).concat(phone3);
        }
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
    }

    public static PhoneAttribute createAttribute(String phone1, String phone2, String phone3) {
        return new PhoneAttribute(phone1, phone2, phone3);
    }
}
