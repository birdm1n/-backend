package com.daema.core.sms.domain;


import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "courtProctorId")
@NoArgsConstructor
@AllArgsConstructor
@Entity  //  aggregate ...
@org.hibernate.annotations.Table(appliesTo = "court_proctor", comment = "법정 대리인")
public class CourtProctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_proctor_id", columnDefinition = "BIGINT UNSIGNED comment '법정 대리인 아이디'")
    private Long courtProctorId;

    @Column(name = "name", columnDefinition = "varchar(255) comment '이름'")
    private String name;

    @Column(name = "email", columnDefinition = "varchar(255) comment '이메일'")
    private String email;

    @Column(name = "regist_num", columnDefinition = "INT comment '주민등록번호'")
    private int registNo;

    @Column(name = "phone_num", columnDefinition = "INT comment '휴대폰 번호'")
    private int phoneNo;

    @Column(name = "relationship", columnDefinition = "varchar(255) comment '관계'")
    private String relationship;

    @OneToOne(mappedBy = "courtProctor")
    private Customer customer;
}
