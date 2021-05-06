package com.daema.wms.repository;

import com.daema.wms.domain.DeviceStatus;
import com.daema.wms.repository.custom.CustomDeviceStatusRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> , CustomDeviceStatusRepository {

}
