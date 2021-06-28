package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.OpenStoreSaleStoreMap;

import java.util.List;

public interface CustomOpenStoreSaleStoreMapRepository {

	List<OpenStoreSaleStoreMap> getMappingList(long storeId);
}
