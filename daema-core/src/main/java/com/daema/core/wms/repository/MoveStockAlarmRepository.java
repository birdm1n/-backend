package com.daema.core.wms.repository;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.domain.MoveStockAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveStockAlarmRepository extends JpaRepository<MoveStockAlarm, Long> {
    MoveStockAlarm findByStore(Store store);
}
