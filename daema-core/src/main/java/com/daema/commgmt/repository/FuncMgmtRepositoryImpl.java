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
    public List<String> getMemberEnableUrlPathList(long memberSeq) {


        JPQLQuery<FuncMgmt> query = from(funcMgmt);

        query.select(Projections.fields(
                FuncMgmt.class
                ,funcMgmt.urlPath
                )
        );

        query.innerJoin(memberRole)
                .on(memberRole.seq.eq(memberSeq));

        query.innerJoin(funcRoleMap)
                .on(memberRole.seq.eq(memberSeq)
                        .and(funcRoleMap.roleId.eq(memberRole.roleId))
                        .and(funcRoleMap.funcId.eq(funcMgmt.funcId))
                );

        return Optional.ofNullable(query.fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(FuncMgmt::getUrlPath)
                .collect(Collectors.toList());
    }
}



















