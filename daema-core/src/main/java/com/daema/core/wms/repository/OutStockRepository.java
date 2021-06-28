package com.daema.core.wms.repository;

import com.daema.core.wms.domain.OutStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutStockRepository extends JpaRepository<OutStock, Long> {
    
}
