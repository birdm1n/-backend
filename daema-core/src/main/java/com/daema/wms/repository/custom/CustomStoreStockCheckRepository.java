package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.response.StoreStockCheckListDto;

import java.util.List;

public interface CustomStoreStockCheckRepository {
    List<StoreStockCheckListDto> getStoreStockCheckHistory(Long storeStockId);
}
