package com.daema.rest.wms.dto.response;

import com.daema.rest.wms.dto.StockMgmtDto;
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
	private List<StockMgmtDto> stockList;
}
