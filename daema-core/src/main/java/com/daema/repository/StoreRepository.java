package com.daema.repository;

import com.daema.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> , CustomStoreRepository{
    List<Store> findByUseYnOrderByStoreName(String useYn);
}
