package com.daema.commgmt.repository;

import com.daema.commgmt.domain.StoreMap;
import com.daema.commgmt.domain.pk.StoreMapPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMapRepository extends JpaRepository<StoreMap, StoreMapPK> {
}
