package com.daema.core.wms.repository;

import com.daema.core.wms.domain.MoveStock;
import com.daema.core.wms.repository.custom.CustomMoveStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveStockRepository extends JpaRepository<MoveStock, Long> , CustomMoveStockRepository {

}
