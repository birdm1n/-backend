package com.daema.core.wms.repository;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.domain.Device;
import com.daema.core.wms.domain.InStock;
import com.daema.core.wms.repository.custom.CustomInStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InStockRepository extends JpaRepository<InStock, Long> , CustomInStockRepository {

    InStock findByStoreAndDeviceAndDelYn(Store store, Device device, String delYn);
}
