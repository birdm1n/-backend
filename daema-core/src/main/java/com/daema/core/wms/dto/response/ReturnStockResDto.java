package com.daema.core.wms.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnStockResDto {

	private Long dvcId;
	private Integer returnStockAmt;
	private String returnStockMemo;
	private DeviceStatusResDto returnDeviceStatus;
	private Long prevStockId;
	private String rawBarcode;

}



































