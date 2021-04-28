package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.SelectStockListDto;
import com.daema.wms.domain.dto.response.StockListDto;

import java.util.List;

public interface CustomStockRepository {
    List<StockListDto> getStockList(StockRequestDto requestDto);

    List<SelectStockListDto> selectStockList(long storeId, Integer telecom);
}
