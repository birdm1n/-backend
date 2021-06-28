package com.daema.core.wms.repository.custom;

import com.daema.core.wms.dto.request.StoreStockRequestDto;
import com.daema.core.wms.dto.response.StoreStockResponseDto;
import org.springframework.data.domain.Page;

public interface CustomStoreStockRepository {

    Page<StoreStockResponseDto> getStoreStockList(StoreStockRequestDto requestDto);
    Page<StoreStockResponseDto> getLongTimeStoreStockList(StoreStockRequestDto requestDto);
    Page<StoreStockResponseDto> getFaultyStoreStockList(StoreStockRequestDto requestDto);
}
