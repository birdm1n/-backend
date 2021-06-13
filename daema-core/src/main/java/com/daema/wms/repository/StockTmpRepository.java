package com.daema.wms.repository;

import com.daema.wms.domain.StockTmp;
import com.daema.wms.repository.custom.CustomStockTmpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTmpRepository extends JpaRepository<StockTmp, Long>, CustomStockTmpRepository {
}
