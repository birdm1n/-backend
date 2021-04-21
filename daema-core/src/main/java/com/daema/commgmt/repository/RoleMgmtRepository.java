package com.daema.commgmt.repository;

import com.daema.commgmt.domain.RoleMgmt;
import com.daema.commgmt.repository.custom.CustomRoleMgmtRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMgmtRepository extends JpaRepository<RoleMgmt, Integer>, CustomRoleMgmtRepository {
}
