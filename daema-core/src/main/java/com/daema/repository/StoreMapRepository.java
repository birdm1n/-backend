package com.daema.repository;

import com.daema.domain.StoreMap;
import com.daema.domain.pk.StoreMapPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMapRepository extends JpaRepository<StoreMap, StoreMapPK> {
    StoreMap findByStoreIdAndParentStoreId(long storeId, long parentStoreId);
}
