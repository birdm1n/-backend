package com.daema.wms.domain.dto.response;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStatusResDto {

	private Long dvcStatusId;
	private String productFaultyYn;
	private WmsEnum.DeviceExtrrStatus extrrStatus;
	private String productMissYn;
	private String missProduct;
	private Integer ddctAmt;
	private Integer addDdctAmt;
	private String ddctReleaseAmtYn;
	private WmsEnum.InStockStatus inStockStatus;
}



































