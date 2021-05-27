package com.daema.wms.repository;

import com.daema.wms.domain.StoreStockHistory;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomStoreStockHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockHistoryRepository extends JpaRepository<StoreStockHistory, Long> , CustomStoreStockHistoryRepository {
    StoreStockHistory findByStockTypeAndStockTypeId(WmsEnum.StockType stockType, Long moveStockId);
}
