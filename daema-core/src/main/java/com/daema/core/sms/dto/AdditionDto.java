package com.daema.core.sms.dto;


import com.daema.core.sms.domain.Addition;
import com.daema.core.sms.domain.JoinInfo;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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



}
