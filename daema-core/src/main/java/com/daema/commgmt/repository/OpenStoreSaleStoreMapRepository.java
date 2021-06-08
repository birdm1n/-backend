package com.daema.commgmt.repository;

import com.daema.commgmt.domain.OpenStoreSaleStoreMap;
import com.daema.commgmt.domain.pk.OpenStoreSaleStoreMapPK;
import com.daema.commgmt.repository.custom.CustomOpenStoreSaleStoreMapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenStoreSaleStoreMapRepository extends JpaRepository<OpenStoreSaleStoreMap, OpenStoreSaleStoreMapPK>, CustomOpenStoreSaleStoreMapRepository {

    Optional<OpenStoreSaleStoreMap> findByOpeningStoreIdAndSaleStoreId(long openStoreId, long saleStoreId);
}
