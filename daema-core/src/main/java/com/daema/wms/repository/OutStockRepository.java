package com.daema.wms.repository;

import com.daema.wms.domain.OutStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutStockRepository extends JpaRepository<OutStock, Long> {
    
}
