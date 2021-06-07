package com.daema.wms.repository;

import com.daema.wms.domain.Device;
import com.daema.wms.repository.custom.CustomDeviceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> , CustomDeviceRepository {

}
