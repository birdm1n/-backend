package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.OpenStoreUserMap;

import java.util.List;

public interface CustomOpenStoreUserMapRepository {

	List<OpenStoreUserMap> getMappingList(long storeId);
}
