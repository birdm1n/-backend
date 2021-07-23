package com.daema.core.scm.domain.customer;


import com.daema.core.scm.domain.vo.PhoneAttribute;
import com.daema.core.scm.dto.ApplicationCustomerDto;
import com.daema.core.scm.dto.CourtProctorAttributeDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CustomerCourtProctorAttribute {

    @Column(name = "court_proctor_name", columnDefinition = "varchar(255) comment '법정 대리인 이름'")
    private String courtProctorName;

    @Column(name = "court_proctor_regi_num1", columnDefinition = "varchar(255) comment '법정 대리인 등록 번호1'")
    private String courtProctorRegiNum1;

    @Column(name = "court_proctor_regi_num2", columnDefinition = "varchar(255) comment '법정 대리인 등록 번호2'")
    private String courtProctorRegiNum2;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(name = "court_proctor_phone", columnDefinition = "varchar(255) comment '법정 대리인 연락처'")),
            @AttributeOverride(name = "phone1", column = @Column(name = "court_proctor_phone2", columnDefinition = "varchar(255) comment '법정 대리인 연락처1'")),
            @AttributeOverride(name = "phone2", column = @Column(name = "court_proctor_phone3", columnDefinition = "varchar(255) comment '법정 대리인 연락처2'")),
            @AttributeOverride(name = "phone3", column = @Column(name = "court_proctor_phone4", columnDefinition = "varchar(255) comment '법정 대리인 연락처3'"))

    })
    private PhoneAttribute courtProctorPhone;

    @Column(name = "court_proctor_relation", columnDefinition = "varchar(255) comment '관계'")
    private String courtProctorRelation;

    /*public static CourtProctorAttributeDto from(CustomerCourtProctorAttribute customerCourtProctorAttribute) {
        return CourtProctorAttributeDto.builder()
                .courtProctorId(customerCourtProctorAttribute.getCourtProctorId())
                .name(customerCourtProctorAttribute.getName())
                .email(customerCourtProctorAttribute.getEmail())
                .registNo(customerCourtProctorAttribute.getRegistNo())
                .courtProctorPhone(customerCourtProctorAttribute.getCourtProctorPhone().getPhone())
                .courtProctorPhone1(customerCourtProctorAttribute.getCourtProctorPhone().getPhone1())
                .courtProctorPhone2(customerCourtProctorAttribute.getCourtProctorPhone().getPhone2())
                .courtProctorPhone3(customerCourtProctorAttribute.getCourtProctorPhone().getPhone3())
                .relationship(customerCourtProctorAttribute.getRelationship())
                .build();
    }
*/
    public static CustomerCourtProctorAttribute create(CourtProctorAttributeDto courtProctorAttribute) {
        return CustomerCourtProctorAttribute.builder()
                .courtProctorName(courtProctorAttribute.getCourtProctorName())
                .courtProctorRegiNum1(courtProctorAttribute.getCourtProctorRegiNum1())
                .courtProctorRegiNum2(courtProctorAttribute.getCourtProctorRegiNum2())
                .courtProctorPhone(PhoneAttribute.createAttribute(courtProctorAttribute.getCourtProctorPhone1(), courtProctorAttribute.getCourtProctorPhone2(), courtProctorAttribute.getCourtProctorPhone3()))
                .courtProctorRelation(courtProctorAttribute.getCourtProctorRelation())
                .build();
    }

}
