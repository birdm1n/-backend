package com.daema.core.sms.dto;

import com.daema.core.sms.domain.CourtProctor;
import com.daema.core.sms.domain.Customer;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtProctorDto {

    private Long courtProctorId;
    private String name;
    private String email;
    private int registNo;
    private int phoneNo;
    private String relationship;
    private Customer customer;

    public static CourtProctorDto from(CourtProctor courtProctor) {
        return CourtProctorDto.builder()
                .courtProctorId(courtProctor.getCourtProctorId())
                .name(courtProctor.getName())
                .email(courtProctor.getEmail())
                .registNo(courtProctor.getRegistNo())
                .phoneNo(courtProctor.getPhoneNo())
                .relationship(courtProctor.getRelationship())
                .customer(courtProctor.getCustomer())
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
                .customer(courtProctorDto.getCustomer())
                .build();
    }
}
