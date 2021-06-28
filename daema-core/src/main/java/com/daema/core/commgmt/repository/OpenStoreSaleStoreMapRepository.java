package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.OpenStoreSaleStoreMap;
import com.daema.core.commgmt.domain.pk.OpenStoreSaleStoreMapPK;
import com.daema.core.commgmt.repository.custom.CustomOpenStoreSaleStoreMapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenStoreSaleStoreMapRepository extends JpaRepository<OpenStoreSaleStoreMap, OpenStoreSaleStoreMapPK>, CustomOpenStoreSaleStoreMapRepository {

    Optional<OpenStoreSaleStoreMap> findByOpeningStoreIdAndSaleStoreId(long openStoreId, long saleStoreId);
}
