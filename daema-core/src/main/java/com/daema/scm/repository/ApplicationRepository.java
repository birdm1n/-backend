package com.daema.scm.repository;

import com.daema.wms.domain.Stock;
import com.daema.wms.repository.custom.CustomStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> , CustomStockRepository {
    Stock findByStockIdAndDelYn(Long stockId, String statusMsg);
    Stock findTopByRegiStoreIdAndStockTypeAndDelYn(Long storeId, String stockType, String delYn);
}
