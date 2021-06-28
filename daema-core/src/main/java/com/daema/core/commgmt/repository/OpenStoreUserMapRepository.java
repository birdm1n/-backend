package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.OpenStoreUserMap;
import com.daema.core.commgmt.domain.pk.OpenStoreUserMapPK;
import com.daema.core.commgmt.repository.custom.CustomOpenStoreUserMapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenStoreUserMapRepository extends JpaRepository<OpenStoreUserMap, OpenStoreUserMapPK>, CustomOpenStoreUserMapRepository {
}
