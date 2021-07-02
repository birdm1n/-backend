package com.daema.core.sms.dto;


import com.daema.core.sms.domain.Addition;
import com.daema.core.sms.domain.JoinInfo;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionDto {
    private Long additionId;
    private SmsEnum.AdditionCategory additionCategory;
    private String productName;
    private int charge;
    private JoinInfo joinInfo;

    public static AdditionDto from(Addition addition) {
        return AdditionDto.builder()
                .additionId(addition.getAdditionId())
                .additionCategory(addition.getAdditionCategory())
                .productName(addition.getProductName())
                .charge(addition.getCharge())
                .joinInfo(addition.getJoinInfo())
                .build();
    }

    public static Addition toEntity(AdditionDto additionDto) {
        return Addition.builder()
                .additionId(additionDto.getAdditionId())
                .additionCategory(additionDto.getAdditionCategory())
                .productName(additionDto.getProductName())
                .charge(additionDto.getCharge())
                .joinInfo(additionDto.getJoinInfo())
                .build();
    }
}
