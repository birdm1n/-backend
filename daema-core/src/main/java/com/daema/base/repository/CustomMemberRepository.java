package com.daema.base.repository;

import com.daema.base.domain.Members;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface CustomMemberRepository {
	List<Members> findByMember(long storeId, OrderSpecifier orderSpecifier);
}
