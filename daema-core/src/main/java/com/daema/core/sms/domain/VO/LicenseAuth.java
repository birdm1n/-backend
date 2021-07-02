package com.daema.core.sms.domain.VO;

import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LicenseAuth {

    @Enumerated(EnumType.STRING)
    @Column(name = "license_type", columnDefinition = "varchar(255) comment '신분증 유형'")
    private SmsEnum.LicenseType licenseType;

    @Column(name = "issue_date", columnDefinition = "INT comment '발급일자'")
    private int issueDate;

    @Column(name = "expired_date", columnDefinition = "INT comment '만료일자'")
    private int expiredDate;

    @Column(name = "foreign_regiNum", columnDefinition = "INT comment '외국인 등록번호'")
    private int foreignRegiNo;

    @Column(name = "area", columnDefinition = "varchar(255) comment '지역'")
    private SmsEnum.Area area;

    @Column(name =  "license_num", columnDefinition = "INT comment '면허번호'")
    private int licenseNo;

    @Column(name =  "stay_code", columnDefinition = "INT comment '체류코드'")
    private int stayCode;

    @Column(name = "contury", columnDefinition = "varchar(255) comment '국가'")
    private String country;


}
