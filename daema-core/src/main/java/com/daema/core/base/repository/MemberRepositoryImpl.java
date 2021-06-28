package com.daema.core.base.repository;

import com.daema.core.base.domain.Members;
import com.daema.core.base.domain.QMembers;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    public MemberRepositoryImpl() {
        super(Members.class);
    }

    @Override
    public List<Members> findByMember(long storeId, OrderSpecifier orderSpecifier) {
        QMembers member = QMembers.members;

        JPQLQuery<Members> query = from(member);

        query.where(
                member.storeId.eq(storeId)
                .and(member.userStatus.eq("6"))
        );

        query.orderBy(orderSpecifier);

        return query.fetch();
    }
}
