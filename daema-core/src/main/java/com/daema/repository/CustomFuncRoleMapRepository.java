package com.daema.repository;

import com.daema.domain.FuncRoleMap;

import java.util.List;

public interface CustomFuncRoleMapRepository {

	List<FuncRoleMap> getMappingList(long storeId);
}
