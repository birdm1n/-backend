package com.daema.core.base.repository;

import com.daema.core.base.domain.Members;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface CustomMemberRepository {
	List<Members> findByMember(long storeId, OrderSpecifier orderSpecifier);
}
