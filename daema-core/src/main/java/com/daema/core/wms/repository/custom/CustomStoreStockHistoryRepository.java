package com.daema.core.wms.repository.custom;

import com.daema.core.wms.domain.StoreStock;

public interface CustomStoreStockHistoryRepository {
    void arrangeStoreStockHistory(StoreStock storeStock, boolean delFlag);
}
