package com.daema.wms.repository;

import com.daema.wms.domain.Stock;
import com.daema.wms.repository.custom.CustomStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> , CustomStockRepository {
}
