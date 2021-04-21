package com.daema.commgmt.repository;

import com.daema.commgmt.domain.OpenStoreUserMap;
import com.daema.commgmt.domain.pk.OpenStoreUserMapPK;
import com.daema.commgmt.repository.custom.CustomOpenStoreUserMapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenStoreUserMapRepository extends JpaRepository<OpenStoreUserMap, OpenStoreUserMapPK>, CustomOpenStoreUserMapRepository {
}
