package com.daema.wms.repository;

import com.daema.wms.domain.ReturnStock;
import com.daema.wms.repository.custom.CustomReturnStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnStockRepository extends JpaRepository<ReturnStock, Long>, CustomReturnStockRepository {
}
