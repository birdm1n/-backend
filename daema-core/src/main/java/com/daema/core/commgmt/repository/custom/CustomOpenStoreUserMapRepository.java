package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.OpenStoreUserMap;

import java.util.List;

public interface CustomOpenStoreUserMapRepository {

	List<OpenStoreUserMap> getMappingList(long storeId);
}
