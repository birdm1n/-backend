package com.daema.rest.wms.dto;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStatusDto {

	private Long dvcStatusId;
	private String productFaultyYn;
	private WmsEnum.DeviceExtrrStatus extrrStatus;
	private String productMissYn;
	private String missProduct;
	private Integer ddctAmt;
	private Integer addDdctAmt;
}



































