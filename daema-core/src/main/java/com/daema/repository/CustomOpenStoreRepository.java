package com.daema.repository;

import com.daema.domain.OpenStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomOpenStoreRepository {
	
	Page<OpenStore> getSearchPage(Pageable pageable);
	List<OpenStore> getOpenStoreList(long storeId);
	OpenStore findOpenStoreInfo(long openStoreId, long storeId);
}
