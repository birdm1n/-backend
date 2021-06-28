package com.daema.core.wms.repository;

import com.daema.core.wms.domain.Device;
import com.daema.core.wms.domain.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {
    List<DeviceStatus> findByDevice(Device device);
}
