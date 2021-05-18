package com.daema.wms.repository;

import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.InStock;
import com.daema.wms.repository.custom.CustomInStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InStockRepository extends JpaRepository<InStock, Long> , CustomInStockRepository {

    InStock findByStoreAndDeviceAndDelYn(Store store, Device device, String delYn);
}
