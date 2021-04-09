package com.daema.repository;

import com.daema.domain.FuncRoleMap;
import com.daema.domain.pk.FuncRoleMapPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncRoleMapRepository extends JpaRepository<FuncRoleMap, FuncRoleMapPK>, CustomFuncRoleMapRepository {

}
