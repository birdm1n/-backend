package com.daema.base.repository;

import com.daema.commgmt.domain.MembersRole;
import com.daema.commgmt.domain.pk.MemberRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MembersRole, MemberRolePK> {
    void deleteBySeq(long seq);
}

