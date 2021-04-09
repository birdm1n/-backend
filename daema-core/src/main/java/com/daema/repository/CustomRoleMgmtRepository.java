package com.daema.repository;

import com.daema.domain.RoleMgmt;

import java.util.List;

public interface CustomRoleMgmtRepository {
	List<RoleMgmt> getRoleList(long storeId);
}
