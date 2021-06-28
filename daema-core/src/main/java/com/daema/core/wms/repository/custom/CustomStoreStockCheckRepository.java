package com.daema.core.wms.repository.custom;

import com.daema.core.wms.dto.response.StoreStockCheckListDto;

import java.util.List;

public interface CustomStoreStockCheckRepository {
    List<StoreStockCheckListDto> getStoreStockCheckHistory(Long storeStockId);
}
