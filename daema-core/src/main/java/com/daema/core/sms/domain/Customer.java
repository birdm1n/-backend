package com.daema.core.sms.domain;


import com.daema.core.sms.domain.VO.Address;
import com.daema.core.sms.domain.VO.LicenseAuth;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "customerId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "customer", comment = "고객")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "BIGINT UNSIGNED comment '고객 아이디'")
    private Long customerId;

    @Column(name = "regist_num", columnDefinition = "INT comment '주민등록번호'")
    private int registNo;

    @Column(name = "charge_reduct", columnDefinition = "INT comment '요금 감면'")
    private int chargeReduct;

    @Column(name = "phone_num", columnDefinition = "INT comment '휴대폰 번호'")
    private int phoneNo;

    @Column(name = "emg_phone", columnDefinition = "INT comment '비상연락망'")
    private int emgPhone;

    @Column(name = "email", columnDefinition = "varchar(255) comment '이메일'")
    private String email;

    @Embedded
    private Address cusAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", columnDefinition = "varchar(255) comment '고객 유형'")
    private SmsEnum.CustomerType customerType;

    @Embedded
    private LicenseAuth licenseAuth;

     @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "court_proctor_id", columnDefinition = "BIGINT UNSIGNED comment '법정 대리인 아이디'")
    private CourtProctor courtProctor;




}

