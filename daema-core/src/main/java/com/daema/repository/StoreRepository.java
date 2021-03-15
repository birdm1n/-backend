package com.daema.repository;

import com.daema.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> , CustomStoreRepository{
}
