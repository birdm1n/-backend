package com.daema.wms.repository;

import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.repository.custom.CustomDeviceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> , CustomDeviceRepository {
    Device findByFullBarcodeAndStoreAndDelYn(String fullBarcode, Store store, String delYn);
    Optional<Device> findByStoreAndFullBarcodeAndDelYn(Store store, String fullBarcode, String delYn);
}
