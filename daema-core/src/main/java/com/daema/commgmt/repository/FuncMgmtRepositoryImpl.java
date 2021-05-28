package com.daema.commgmt.repository;

import com.daema.commgmt.domain.FuncMgmt;
import com.daema.commgmt.repository.custom.CustomFuncMgmtRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.daema.commgmt.domain.QFuncMgmt.funcMgmt;
import static com.daema.commgmt.domain.QFuncRoleMap.funcRoleMap;
import static com.daema.commgmt.domain.QMemberRole.memberRole;

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
                .innerJoin(memberRole)
                .on(funcRoleMap.roleId.eq(memberRole.roleId)
                        .and(memberRole.seq.eq(memberSeq))
                );

        query.groupBy(funcRoleMap.funcId);

        return Optional.ofNullable(query.fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(FuncMgmt::getUrlPath)
                .collect(Collectors.toList());
    }
}



















