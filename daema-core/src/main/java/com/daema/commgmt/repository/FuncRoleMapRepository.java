package com.daema.commgmt.repository;

import com.daema.commgmt.domain.FuncRoleMap;
import com.daema.commgmt.domain.pk.FuncRoleMapPK;
import com.daema.commgmt.repository.custom.CustomFuncRoleMapRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncRoleMapRepository extends JpaRepository<FuncRoleMap, FuncRoleMapPK>, CustomFuncRoleMapRepository {

}
