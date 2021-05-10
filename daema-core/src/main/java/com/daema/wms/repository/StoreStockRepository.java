package com.daema.wms.repository;

import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.repository.custom.CustomStoreStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockRepository extends JpaRepository<StoreStock, Long> , CustomStoreStockRepository {
    StoreStock findByStoreAndDevice(Store store, Device device);
}
