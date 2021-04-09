package com.daema.repository;

import com.daema.domain.RoleMgmt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMgmtRepository extends JpaRepository<RoleMgmt, Integer>, CustomRoleMgmtRepository {
}
