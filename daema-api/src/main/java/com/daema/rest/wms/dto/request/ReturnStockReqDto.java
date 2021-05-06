package com.daema.rest.wms.dto.request;

import com.daema.rest.wms.dto.DeviceStatusDto;
import com.daema.rest.wms.dto.DeviceStockDto;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnStockReqDto {

	private Long returnStockId;
	private WmsEnum.InStockStatus inStockStatus;
	private Integer returnStockAmt;
	private String returnStockMemo;
	private String ddctReleaseAmtYn;
	private Long dvcId;
	private DeviceStockDto deviceStockDto;
	private DeviceStatusDto deviceStatusDto;
}



































