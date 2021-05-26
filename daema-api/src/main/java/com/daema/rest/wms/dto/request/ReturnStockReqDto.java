package com.daema.rest.wms.dto.request;

import com.daema.rest.wms.dto.DeviceStatusDto;
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



































