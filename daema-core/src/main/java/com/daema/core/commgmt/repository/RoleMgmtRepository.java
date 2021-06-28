package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.RoleMgmt;
import com.daema.core.commgmt.repository.custom.CustomRoleMgmtRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMgmtRepository extends JpaRepository<RoleMgmt, Long>, CustomRoleMgmtRepository {
}
