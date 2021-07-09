package com.daema.core.sms.dto;


import com.daema.core.base.domain.Members;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicInfoDto {

    private Long basicInfoId;
    private Long members;

}
