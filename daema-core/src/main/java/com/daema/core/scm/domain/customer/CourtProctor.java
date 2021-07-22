package com.daema.core.scm.domain.customer;


import com.daema.core.scm.domain.vo.PhoneAttribute;
import com.daema.core.scm.dto.CourtProctorDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "courtProctorId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(name = "court_proctor_phone", columnDefinition = "varchar(255) comment '개통 연락처'")),
            @AttributeOverride(name = "phone1", column = @Column(name = "court_proctor_phone2", columnDefinition = "varchar(255) comment '개통 연락처1'")),
            @AttributeOverride(name = "phone2", column = @Column(name = "court_proctor_phone3", columnDefinition = "varchar(255) comment '개통 연락처2'")),
            @AttributeOverride(name = "phone3", column = @Column(name = "court_proctor_phone4", columnDefinition = "varchar(255) comment '개통 연락처3'"))

    })
    private PhoneAttribute courtProctorPhone;

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
                .courtProctorPhone(courtProctor.getCourtProctorPhone().getPhone())
                .courtProctorPhone1(courtProctor.getCourtProctorPhone().getPhone1())
                .courtProctorPhone2(courtProctor.getCourtProctorPhone().getPhone2())
                .courtProctorPhone3(courtProctor.getCourtProctorPhone().getPhone3())
                .relationship(courtProctor.getRelationship())
                .build();
    }

    public static CourtProctor create(CourtProctorDto courtProctorDto) {
        return CourtProctor.builder()
                .name(courtProctorDto.getName())
                .email(courtProctorDto.getEmail())
                .registNo(courtProctorDto.getRegistNo())
                .courtProctorPhone(PhoneAttribute.createAttribute(
                        courtProctorDto.getCourtProctorPhone1()
                        ,courtProctorDto.getCourtProctorPhone2()
                        ,courtProctorDto.getCourtProctorPhone3())
                        )
                .relationship(courtProctorDto.getRelationship())
                .build();
    }

}
