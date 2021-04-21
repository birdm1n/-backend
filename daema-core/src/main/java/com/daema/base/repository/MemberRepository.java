package com.daema.base.repository;

import com.daema.base.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, CustomMemberRepository {

    Member findByUsername(String username);
    Member findByUsernameAndUserStatus(String username, String userStatus);
    List<Member> findByOrgId(long orgId);
}

