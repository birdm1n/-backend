package com.daema.repository;

import com.daema.domain.AddService;
import com.daema.domain.QAddService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class AddServiceRepositoryImpl extends QuerydslRepositorySupport implements CustomAddServiceRepository {

    public AddServiceRepositoryImpl() {
        super(AddService.class);
    }

    @Override
    public Page<AddService> getSearchPage(Pageable pageable, boolean isAdmin) {

        QAddService addService = QAddService.addService;

        JPQLQuery<AddService> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(addService.delYn.eq("N"));

        if(!isAdmin){
            builder.and(addService.useYn.eq("Y"));
        }

        query.select(Projections.fields(
                AddService.class
                ,addService.addSvcId
                ,addService.addSvcName
                ,addService.addSvcCharge
                ,addService.telecom
                ,addService.originKey
                ,addService.addSvcMemo
                ,addService.useYn
                ,addService.delYn
                ,addService.regiDateTime
                )
        );

        query.from(addService)
                .where(builder)
                .orderBy(addService.regiDateTime.desc());

        query.offset(pageable.getOffset())
        .limit(pageable.getPageSize());

        List<AddService> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }
}
