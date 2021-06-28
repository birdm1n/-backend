package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.commgmt.repository.custom.CustomStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> , CustomStoreRepository {
    List<Store> findByUseYnOrderByStoreName(String useYn);
    Store findByStoreName(String storeName);
    Store findByBizNoAndUseYn(String bizNo, String useYn);
}
