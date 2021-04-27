package com.daema.commgmt.repository;

import com.daema.commgmt.domain.FuncMgmt;
import com.daema.commgmt.repository.custom.CustomFuncMgmtRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncMgmtRepository extends JpaRepository<FuncMgmt, String>, CustomFuncMgmtRepository {
    List<FuncMgmt> findAllByRoleContainingOrderByGroupIdAscRoleAscOrderNumAsc(String role);
}
