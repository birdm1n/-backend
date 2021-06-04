package com.daema.base.repository;

import com.daema.base.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Members, Long>, CustomMemberRepository {

    Members findByUsername(String username);
    List<Members> findByOrgId(long orgId);
}

