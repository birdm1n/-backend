package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.WaitInStockDto;

import java.util.List;

public interface CustomInStockRepository {
    List<WaitInStockDto> getWaitInStockList(InStockRequestDto requestDto);
}
