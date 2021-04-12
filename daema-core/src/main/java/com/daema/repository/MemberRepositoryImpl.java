package com.daema.repository;

import com.daema.domain.Member;
import com.daema.domain.QMember;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public List<Member> findByMember(long storeId, OrderSpecifier orderSpecifier) {
        QMember member = QMember.member;

        JPQLQuery<Member> query = from(member);

        query.where(
                member.storeId.eq(storeId)
                .and(member.userStatus.eq("6"))
        );

        query.orderBy(orderSpecifier);

        return query.fetch();
    }
}
