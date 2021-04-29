package com.daema.rest.wms.dto.response;

import com.daema.wms.domain.InStock;
import com.daema.wms.domain.InStockWait;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InStockMgmtResponseDto {

	public List<InStockWait> inStockWaitList;
	public List<InStock> inStockList;
}
