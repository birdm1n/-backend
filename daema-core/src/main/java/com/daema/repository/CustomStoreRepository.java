package com.daema.repository;

import com.daema.domain.Store;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomStoreRepository {
	
	Page<Store> getSearchPage(Pageable pageable);
	Store findStoreInfo(long parentStoreId, long storeId);
	List<Store> findBySaleStore(long parentStoreId, OrderSpecifier orderSpecifier);
}
