package com.daema.commgmt.repository;

import com.daema.base.enums.StatusEnum;
import com.daema.commgmt.domain.QRoleMgmt;
import com.daema.commgmt.domain.RoleMgmt;
import com.daema.commgmt.repository.custom.CustomRoleMgmtRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RoleMgmtRepositoryImpl extends QuerydslRepositorySupport implements CustomRoleMgmtRepository {

    public RoleMgmtRepositoryImpl() {
        super(RoleMgmt.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RoleMgmt> getRoleList(long storeId) {

        QRoleMgmt roleMgmt = QRoleMgmt.roleMgmt;

        JPQLQuery<RoleMgmt> query = getQuerydsl().createQuery();
        query.from(roleMgmt);

        BooleanBuilder where = new BooleanBuilder();
        where.and(roleMgmt.necessaryYn.eq(StatusEnum.FLAG_Y.getStatusMsg())
                .and(roleMgmt.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())));

        BooleanBuilder where2 = new BooleanBuilder();
        where2.and(roleMgmt.storeId.eq(storeId))
                .and(roleMgmt.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()));

        query.where(where.or(Expressions.predicate(Ops.WRAPPED, where2)));

        query.orderBy(
                roleMgmt.necessaryYn.desc()
                ,roleMgmt.roleName.asc()
        );

        return query.fetch();
    }
}












