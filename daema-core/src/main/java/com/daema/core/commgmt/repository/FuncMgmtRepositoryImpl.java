package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.FuncMgmt;
import com.daema.core.commgmt.repository.custom.CustomFuncMgmtRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.daema.core.commgmt.domain.QFuncMgmt.funcMgmt;
import static com.daema.core.commgmt.domain.QFuncRoleMap.funcRoleMap;
import static com.daema.core.commgmt.domain.QMembersRole.membersRole;

public class FuncMgmtRepositoryImpl extends QuerydslRepositorySupport implements CustomFuncMgmtRepository {

    public FuncMgmtRepositoryImpl() {
        super(FuncMgmt.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<String> getMemberEnableUrlPathList(long memberSeq, long storeId) {


        JPQLQuery<FuncMgmt> query = from(funcMgmt);

        query.select(Projections.fields(
                FuncMgmt.class
                ,funcMgmt.urlPath
                )
        );

        query.innerJoin(funcRoleMap)
                .on(funcRoleMap.storeId.eq(storeId)
                        .and(funcRoleMap.funcId.eq(funcMgmt.funcId))
                )
                .innerJoin(membersRole)
                .on(funcRoleMap.roleId.eq(membersRole.roleId)
                        .and(membersRole.seq.eq(memberSeq))
                );

        query.groupBy(funcRoleMap.funcId);

        return Optional.ofNullable(query.fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(FuncMgmt::getUrlPath)
                .collect(Collectors.toList());
    }
}



















