package com.daema.wms.repository;

import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.Opening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningRepository extends JpaRepository<Opening, Long> , CustomOpeningRepository {
    /* 개통 상태인 기기 검색용 */
    long countByDeviceAndStoreAndDelYn(Device device, Store store, String delYn);
}
