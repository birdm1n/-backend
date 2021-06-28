package com.daema.core.wms.repository;

import com.daema.core.wms.domain.StockTmp;
import com.daema.core.wms.repository.custom.CustomStockTmpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTmpRepository extends JpaRepository<StockTmp, Long>, CustomStockTmpRepository {
}
