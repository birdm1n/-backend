package com.daema.core.wms.repository;

import com.daema.core.wms.domain.Device;
import com.daema.core.wms.repository.custom.CustomDeviceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> , CustomDeviceRepository {

}
