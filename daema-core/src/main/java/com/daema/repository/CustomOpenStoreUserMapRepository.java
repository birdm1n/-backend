package com.daema.repository;

import com.daema.domain.OpenStoreUserMap;

import java.util.List;

public interface CustomOpenStoreUserMapRepository {

	List<OpenStoreUserMap> getMappingList(long storeId);
}
