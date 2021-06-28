package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.RoleMgmt;

import java.util.List;

public interface CustomRoleMgmtRepository {
	List<RoleMgmt> getRoleList(long storeId);
}
