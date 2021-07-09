package com.daema.core.sms.dto;


import com.daema.core.sms.domain.Addition;
import com.daema.core.sms.domain.JoinAddition;
import com.daema.core.sms.domain.JoinInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinAdditionDto {

    private Long joinAdditionId;
    private Long joinInfoId;
    private Long additionId;


}
