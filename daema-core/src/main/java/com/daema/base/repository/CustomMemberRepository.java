package com.daema.base.repository;

import com.daema.base.domain.Member;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface CustomMemberRepository {
	List<Member> findByMember(long storeId, OrderSpecifier orderSpecifier);
}
