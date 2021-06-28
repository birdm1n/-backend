package com.daema.core.wms.repository;

import com.daema.core.wms.domain.StoreStockHistory;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.repository.custom.CustomStoreStockHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockHistoryRepository extends JpaRepository<StoreStockHistory, Long> , CustomStoreStockHistoryRepository {
    StoreStockHistory findByStockTypeAndStockTypeId(WmsEnum.StockType stockType, Long moveStockId);
}
