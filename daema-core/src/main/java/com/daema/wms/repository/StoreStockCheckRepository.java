package com.daema.wms.repository;

import com.daema.wms.domain.StoreStockCheck;
import com.daema.wms.repository.custom.CustomStoreStockCheckRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockCheckRepository extends JpaRepository<StoreStockCheck, Long> , CustomStoreStockCheckRepository {
}
