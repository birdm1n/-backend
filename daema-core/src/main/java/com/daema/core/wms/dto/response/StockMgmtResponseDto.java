package com.daema.core.wms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMgmtResponseDto {

	private String storeName;
	private List<StockListDto> stockList;
	private List<StockDeviceListDto> stockDeviceList;
}
