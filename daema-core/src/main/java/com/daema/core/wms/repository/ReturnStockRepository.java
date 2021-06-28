package com.daema.core.wms.repository;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.domain.Device;
import com.daema.core.wms.domain.ReturnStock;
import com.daema.core.wms.repository.custom.CustomReturnStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnStockRepository extends JpaRepository<ReturnStock, Long>, CustomReturnStockRepository {
    List<ReturnStock> findByDeviceAndStore(Device device, Store store);
}
