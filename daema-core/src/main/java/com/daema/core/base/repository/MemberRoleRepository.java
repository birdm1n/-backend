package com.daema.core.base.repository;

import com.daema.core.commgmt.domain.MembersRole;
import com.daema.core.commgmt.domain.pk.MemberRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MembersRole, MemberRolePK> {
    void deleteBySeq(long seq);
}

