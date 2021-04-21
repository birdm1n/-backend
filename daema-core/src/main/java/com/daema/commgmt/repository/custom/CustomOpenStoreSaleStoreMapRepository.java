package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.OpenStoreSaleStoreMap;

import java.util.List;

public interface CustomOpenStoreSaleStoreMapRepository {

	List<OpenStoreSaleStoreMap> getMappingList(long storeId);
}
