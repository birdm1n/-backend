package com.daema.rest.wms.dto;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

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



































