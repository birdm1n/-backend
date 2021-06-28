package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.FuncRoleMap;
import com.daema.core.commgmt.domain.pk.FuncRoleMapPK;
import com.daema.core.commgmt.repository.custom.CustomFuncRoleMapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncRoleMapRepository extends JpaRepository<FuncRoleMap, FuncRoleMapPK>, CustomFuncRoleMapRepository {

}
