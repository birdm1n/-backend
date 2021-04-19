package com.daema.repository;

import com.daema.domain.FuncMgmt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncMgmtRepository extends JpaRepository<FuncMgmt, String> {
    List<FuncMgmt> findAllByRoleContainingOrderByGroupIdAscRoleAscOrderNumAsc(String role);
}
