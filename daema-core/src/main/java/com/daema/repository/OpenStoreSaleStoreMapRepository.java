package com.daema.repository;

import com.daema.domain.OpenStoreSaleStoreMap;
import com.daema.domain.pk.OpenStoreSaleStoreMapPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenStoreSaleStoreMapRepository extends JpaRepository<OpenStoreSaleStoreMap, OpenStoreSaleStoreMapPK>, CustomOpenStoreSaleStoreMapRepository {

    Optional<OpenStoreSaleStoreMap> findByOpenStoreIdAndSaleStoreId(long openStoreId, long saleStoreId);
}
