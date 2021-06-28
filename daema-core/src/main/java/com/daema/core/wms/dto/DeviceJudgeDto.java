package com.daema.rest.wms.dto;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceJudgeDto {

	private Long dvcId;
	private String judgeMemo;
	private WmsEnum.JudgementStatus judgeStatus;
}



































