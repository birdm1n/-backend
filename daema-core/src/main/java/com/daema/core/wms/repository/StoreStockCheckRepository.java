package com.daema.core.wms.repository;

import com.daema.core.wms.domain.StoreStockCheck;
import com.daema.core.wms.repository.custom.CustomStoreStockCheckRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockCheckRepository extends JpaRepository<StoreStockCheck, Long> , CustomStoreStockCheckRepository {
}
