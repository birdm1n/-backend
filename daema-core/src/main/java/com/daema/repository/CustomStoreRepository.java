package com.daema.repository;

import com.daema.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomStoreRepository {
	
	public Page<Store> getSearchPage(Pageable pageable);
	public Store findStoreInfo(long parentStoreId, long storeId);
}
