package com.daema.repository;

import com.daema.domain.OpenStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenStoreRepository extends JpaRepository<OpenStore, Long>, CustomOpenStoreRepository {

    Optional<OpenStore> findByOpenStoreIdAndStoreIdAndDelYn(long openStoreId, long storeId, String delYn);
}
