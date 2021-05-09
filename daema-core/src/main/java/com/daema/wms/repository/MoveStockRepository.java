package com.daema.wms.repository;

import com.daema.wms.domain.MoveStock;
import com.daema.wms.repository.custom.CustomMoveStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveStockRepository extends JpaRepository<MoveStock, Long> , CustomMoveStockRepository {

}
