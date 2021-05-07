package com.daema.rest.wms.dto;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnStockDto {

	private WmsEnum.InStockStatus returnStockStatus;
	private Integer returnStockAmt;
	private String returnStockMemo;
	private Long dvcId;
	private DeviceStockDto deviceStockDto;
	private DeviceStatusDto deviceStatusDto;
}



































