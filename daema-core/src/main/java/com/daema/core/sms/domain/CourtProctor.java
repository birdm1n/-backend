package com.daema.core.sms.domain;


import com.daema.core.sms.dto.CourtProctorDto;
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

    public static CourtProctorDto from(CourtProctor courtProctor) {
        return CourtProctorDto.builder()
                .courtProctorId(courtProctor.getCourtProctorId())
                .name(courtProctor.getName())
                .email(courtProctor.getEmail())
                .registNo(courtProctor.getRegistNo())
                .phoneNo(courtProctor.getPhoneNo())
                .relationship(courtProctor.getRelationship())
                /*  .customerId(courtProctor.getCustomer().getCustomerId())*/
                .build();
    }

    public static CourtProctor toEntity(CourtProctorDto courtProctorDto) {
        return CourtProctor.builder()
                .courtProctorId(courtProctorDto.getCourtProctorId())
                .name(courtProctorDto.getName())
                .email(courtProctorDto.getEmail())
                .registNo(courtProctorDto.getRegistNo())
                .phoneNo(courtProctorDto.getPhoneNo())
                .relationship(courtProctorDto.getRelationship())
                /*  .customer(Customer.builder()
                          .customerId(courtProctorDto.getCustomerId())
                          .build())*/
                .build();
    }
    public CourtProctor toEntity(Customer customer) {
        return CourtProctor.builder()
                .courtProctorId(this.getCourtProctorId())
                .name(this.getName())
                .email(this.getEmail())
                .registNo(this.getRegistNo())
                .phoneNo(this.getPhoneNo())
                .relationship(this.getRelationship())
                .customer(customer)
                /*.customer(Customer.builder()
                        .customerId(this.getCustomerId())
                        .build())*/
                .build();
    }
}
