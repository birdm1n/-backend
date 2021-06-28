package com.daema.core.wms.dto;

import com.daema.core.wms.domain.enums.WmsEnum;
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



































