package com.daema.wms.repository;

import com.daema.wms.domain.Device;
import com.daema.wms.domain.DeviceJudge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceJudgeRepository extends JpaRepository<DeviceJudge, Long> {
    List<DeviceJudge> findByDevice(Device device);
}
