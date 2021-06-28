package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.FuncMgmt;
import com.daema.core.commgmt.repository.custom.CustomFuncMgmtRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncMgmtRepository extends JpaRepository<FuncMgmt, String>, CustomFuncMgmtRepository {
    List<FuncMgmt> findAllByRoleContainingOrderByGroupIdAscRoleAscOrderNumAsc(String role);
}
