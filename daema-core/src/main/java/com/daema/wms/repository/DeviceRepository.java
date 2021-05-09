package com.daema.wms.repository;

import com.daema.wms.domain.Device;
import com.daema.wms.repository.custom.CustomDeviceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> , CustomDeviceRepository {
    Device findByFullBarcode(String fullBarcode);
    Optional<Device> findByFullBarcodeAndDelYn(String fullBarcode, String delYn);
}
