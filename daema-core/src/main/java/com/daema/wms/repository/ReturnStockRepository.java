package com.daema.wms.repository;

import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.ReturnStock;
import com.daema.wms.repository.custom.CustomReturnStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnStockRepository extends JpaRepository<ReturnStock, Long>, CustomReturnStockRepository {
    List<ReturnStock> findByDeviceAndStore(Device device, Store store);
}
