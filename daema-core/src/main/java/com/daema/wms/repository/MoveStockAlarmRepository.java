package com.daema.wms.repository;

import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.MoveStockAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveStockAlarmRepository extends JpaRepository<MoveStockAlarm, Long> {
    MoveStockAlarm findByStore(Store store);
}
