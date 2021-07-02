package com.daema.core.sms.dto;

import com.daema.core.sms.domain.Insurance;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceDto {

    private Long insuranceId;

    public static InsuranceDto from(Insurance insurance) {
        return InsuranceDto.builder()
                .insuranceId(insurance.getInsuranceId())

                .build();
    }

    public static Insurance toEntity(InsuranceDto insuranceDto) {
        return Insurance.builder()
                .insuranceId(insuranceDto.getInsuranceId())
                .build();
    }
}
