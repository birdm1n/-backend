package com.daema.repository;

import com.daema.domain.OpenStoreSaleStoreMap;

import java.util.List;

public interface CustomOpenStoreSaleStoreMapRepository {

	List<OpenStoreSaleStoreMap> getMappingList(long storeId);
}
