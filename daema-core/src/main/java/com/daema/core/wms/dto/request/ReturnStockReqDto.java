package com.daema.core.wms.dto.request;

import com.daema.core.wms.dto.DeviceStatusDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnStockReqDto {

	private Long dvcId;
	private Integer returnStockAmt;
	private String returnStockMemo;
	private DeviceStatusDto returnDeviceStatus;
	private Long prevStockId;
	private String rawBarcode;

}



































