package com.daema.wms.repository;

import com.daema.wms.domain.DeviceStock;
import com.daema.wms.repository.custom.CustomDeviceStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStockRepository extends JpaRepository<DeviceStock, Long> , CustomDeviceStockRepository {

}
