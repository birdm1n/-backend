package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.FuncRoleMap;

import java.util.List;

public interface CustomFuncRoleMapRepository {

	List<FuncRoleMap> getMappingList(long storeId);
}
