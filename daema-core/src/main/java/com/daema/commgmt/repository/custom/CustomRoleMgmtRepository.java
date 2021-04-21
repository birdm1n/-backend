package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.RoleMgmt;

import java.util.List;

public interface CustomRoleMgmtRepository {
	List<RoleMgmt> getRoleList(long storeId);
}
