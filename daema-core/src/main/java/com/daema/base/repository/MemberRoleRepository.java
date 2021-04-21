package com.daema.base.repository;

import com.daema.commgmt.domain.MemberRole;
import com.daema.commgmt.domain.pk.MemberRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, MemberRolePK> {
    void deleteBySeq(long seq);
}

