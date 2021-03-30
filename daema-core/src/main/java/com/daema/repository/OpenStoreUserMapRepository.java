package com.daema.repository;

import com.daema.domain.OpenStoreUserMap;
import com.daema.domain.pk.OpenStoreUserMapPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenStoreUserMapRepository extends JpaRepository<OpenStoreUserMap, OpenStoreUserMapPK>, CustomOpenStoreUserMapRepository {
}
