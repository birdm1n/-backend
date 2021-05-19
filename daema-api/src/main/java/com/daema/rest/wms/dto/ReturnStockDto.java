package com.daema.rest.wms.dto;

import com.daema.wms.domain.dto.request.DeviceStatusDto;
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



































