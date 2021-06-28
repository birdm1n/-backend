package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.commgmt.repository.custom.CustomOpenStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenStoreRepository extends JpaRepository<OpenStore, Long>, CustomOpenStoreRepository {

    Optional<OpenStore> findByOpenStoreIdAndStoreIdAndDelYn(long openStoreId, long storeId, String delYn);
}
