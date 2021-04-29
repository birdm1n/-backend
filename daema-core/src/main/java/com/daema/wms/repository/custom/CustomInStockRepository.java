package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;

import java.util.List;

public interface CustomInStockRepository {
    List<InStockWaitDto> getWaitInStockList(InStockRequestDto requestDto);
}
