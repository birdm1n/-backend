package com.daema.wms.repository;

import com.daema.wms.domain.InStock;
import com.daema.wms.repository.custom.CustomInStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InStockRepository extends JpaRepository<InStock, Long> , CustomInStockRepository {
    
}
