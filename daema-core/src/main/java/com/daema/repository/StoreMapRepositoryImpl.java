package com.daema.repository;

import com.daema.domain.StoreMap;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class StoreMapRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreMapRepository {

    public StoreMapRepositoryImpl() {
        super(StoreMap.class);
    }
}
