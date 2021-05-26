package com.daema.rest.wms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnStockDto {

	private Integer returnStockAmt;
	private String returnStockMemo;
	private Long dvcId;
	private MoveStockDto deviceStockDto;
	private DeviceStatusDto deviceStatusDto;
}



































