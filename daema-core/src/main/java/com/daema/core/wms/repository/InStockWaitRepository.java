package com.daema.core.wms.repository;

import com.daema.core.wms.domain.InStockWait;
import com.daema.core.wms.repository.custom.CustomInStockWaitRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InStockWaitRepository extends JpaRepository<InStockWait, Long>, CustomInStockWaitRepository {



}
