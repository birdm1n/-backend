package com.daema.core.wms.repository;

import com.daema.core.wms.domain.Device;
import com.daema.core.wms.domain.DeviceJudge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceJudgeRepository extends JpaRepository<DeviceJudge, Long> {
    List<DeviceJudge> findByDevice(Device device);
}
