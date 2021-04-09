package com.daema.repository;

import com.daema.domain.MemberRole;
import com.daema.domain.pk.MemberRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, MemberRolePK> {
    void deleteBySeq(long seq);
}

