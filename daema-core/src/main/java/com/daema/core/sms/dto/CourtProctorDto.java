package com.daema.core.sms.dto;

import com.daema.core.sms.domain.CourtProctor;
import com.daema.core.sms.domain.Customer;
import com.daema.core.sms.domain.Payment;
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
/*
    private Long customerId;
*/



}
