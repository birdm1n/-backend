package com.daema.wms.domain.dto.request;

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
	private String fullBarcode;

}



































