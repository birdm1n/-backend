package com.daema.wms.repository;

import com.daema.wms.domain.Stock;
import com.daema.wms.repository.custom.CustomStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> , CustomStockRepository {
    Stock findByStockIdAndDelYn(Long stockId, String statusMsg);
}
