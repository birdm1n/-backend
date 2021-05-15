package com.daema.wms.repository.custom;

import com.daema.wms.domain.StoreStock;

public interface CustomStoreStockHistoryRepository {
    void arrangeStoreStockHistory(StoreStock storeStock, boolean delFlag);
}
