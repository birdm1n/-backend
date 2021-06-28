package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.FuncRoleMap;

import java.util.List;

public interface CustomFuncRoleMapRepository {

	List<FuncRoleMap> getMappingList(long storeId);
}
