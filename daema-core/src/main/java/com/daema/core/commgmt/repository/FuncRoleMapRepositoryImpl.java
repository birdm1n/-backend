package com.daema.core.commgmt.repository;

import com.daema.core.base.enums.StatusEnum;
import com.daema.core.commgmt.domain.FuncRoleMap;
import com.daema.core.commgmt.domain.QFuncRoleMap;
import com.daema.core.commgmt.domain.QRoleMgmt;
import com.daema.core.commgmt.repository.custom.CustomFuncRoleMapRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class FuncRoleMapRepositoryImpl extends QuerydslRepositorySupport implements CustomFuncRoleMapRepository {

    public FuncRoleMapRepositoryImpl() {
        super(FuncRoleMap.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<FuncRoleMap> getMappingList(long storeId) {

        QFuncRoleMap funcRoleMap = QFuncRoleMap.funcRoleMap;
        QRoleMgmt roleMgmt = QRoleMgmt.roleMgmt;

        JPQLQuery<FuncRoleMap> query = from(funcRoleMap);

        query.select(Projections.fields(
                FuncRoleMap.class
                ,funcRoleMap.funcId
                ,funcRoleMap.roleId
                ,funcRoleMap.storeId
                )
        );


        BooleanBuilder on = new BooleanBuilder();
        on.and(roleMgmt.storeId.eq(storeId)
                .and(roleMgmt.roleId.eq(funcRoleMap.roleId))
                .and(roleMgmt.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
                .and(funcRoleMap.storeId.eq(storeId)));

        BooleanBuilder on2 = new BooleanBuilder();
        on2.and(roleMgmt.necessaryYn.eq(StatusEnum.FLAG_Y.getStatusMsg())
                .and(roleMgmt.roleId.eq(funcRoleMap.roleId)))
                .and(roleMgmt.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()));

        query.innerJoin(roleMgmt)
                .on(
                        on.or(Expressions.predicate(Ops.WRAPPED, on2))
                );

        return query.fetch();

    }
}



















