package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import com.daema.wms.domain.dto.response.StockListDto;

import java.util.List;

public interface CustomStockRepository {
    List<StockListDto> getStockList(StockRequestDto requestDto);

    List<SelectStockDto> selectStockList(long storeId, Integer telecom);

    SelectStockDto getStock(Long storeId, Integer telecom, Long stockId);

}
