package com.daema.core.wms.repository;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.domain.Device;
import com.daema.core.wms.domain.StoreStock;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.repository.custom.CustomStoreStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockRepository extends JpaRepository<StoreStock, Long> , CustomStoreStockRepository {
    //상점내 재고 여부 확인하고 없으면 인서트 or 업데이트
    StoreStock findByStoreAndDevice(Store store, Device device);

    //상점내 재고 여부 확인하고 있으면 재고조사 or Exception
    StoreStock findByStoreAndDeviceAndStockYn(Store store, Device device, String delYn);

    StoreStock findByStockTypeAndStockTypeIdAndStockYn(WmsEnum.StockType stockType, Long moveStockId, String stockYn);
}
