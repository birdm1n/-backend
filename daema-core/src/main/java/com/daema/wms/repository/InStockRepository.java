package com.daema.wms.repository;

import com.daema.wms.domain.Device;
import com.daema.wms.domain.InStock;
import com.daema.wms.domain.Stock;
import com.daema.wms.repository.custom.CustomInStockRepository;
import com.daema.wms.repository.custom.CustomStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface InStockRepository extends JpaRepository<InStock, Long> , CustomInStockRepository {

}
