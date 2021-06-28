package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.StoreMap;
import com.daema.core.commgmt.domain.pk.StoreMapPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMapRepository extends JpaRepository<StoreMap, StoreMapPK> {
}
