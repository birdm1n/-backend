package com.daema.wms.repository;

import com.daema.wms.domain.Device;
import com.daema.wms.domain.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {
    List<DeviceStatus> findByDevice(Device device);
}
