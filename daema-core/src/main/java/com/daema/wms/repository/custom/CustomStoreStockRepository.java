package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import org.springframework.data.domain.Page;

public interface CustomStoreStockRepository {

    Page<StoreStockResponseDto> getStoreStockList(StoreStockRequestDto requestDto);
}
