package com.daema.core.wms.repository;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.domain.Device;
import com.daema.core.wms.domain.Opening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningRepository extends JpaRepository<Opening, Long> , CustomOpeningRepository {
    /* 개통 상태인 기기 검색용 */
    long countByDeviceAndStoreAndDelYn(Device device, Store store, String delYn);
}
