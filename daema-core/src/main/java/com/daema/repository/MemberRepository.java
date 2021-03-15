package com.daema.repository;

import com.daema.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Member findByUsername(String username);

}

