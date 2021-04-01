package com.daema.repository;

import com.daema.domain.AddServiceRegReq;
import com.daema.domain.AddServiceRegReqReject;
import com.daema.domain.QAddServiceRegReq;
import com.daema.domain.QAddServiceRegReqReject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class AddServiceRegReqRepositoryImpl extends QuerydslRepositorySupport implements CustomAddServiceRegReqRepository {

    public AddServiceRegReqRepositoryImpl() {
        super(AddServiceRegReq.class);
    }

    @Override
    public Page<AddServiceRegReq> getSearchPage(Pageable pageable, boolean isAdmin) {

        QAddServiceRegReq addServiceRegReq = QAddServiceRegReq.addServiceRegReq;
        QAddServiceRegReqReject addServiceRegReqReject = QAddServiceRegReqReject.addServiceRegReqReject;

        JPQLQuery<AddServiceRegReq> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();

        if(!isAdmin){
            //TODO 하드코딩
            builder.and(addServiceRegReq.reqStoreId.eq(1L));
        }

        query.select(Projections.fields(
                AddServiceRegReq.class
                ,addServiceRegReq.addSvcRegReqId
                ,addServiceRegReq.addSvcName
                ,addServiceRegReq.addSvcCharge
                ,addServiceRegReq.addSvcMemo
                ,addServiceRegReq.telecom
                ,addServiceRegReq.reqStatus
                ,addServiceRegReq.reqStoreId
                ,addServiceRegReq.regiDateTime
                ,Projections.fields(AddServiceRegReqReject.class
                        ,addServiceRegReqReject.addSvcRegReqId
                        ,addServiceRegReqReject.rejectComment
                        ,addServiceRegReqReject.rejectDateTime
                        ,addServiceRegReqReject.rejectUserId
                ).as("addServiceRegReqReject")
            )
        );

        query.from(addServiceRegReq)
                .leftJoin(addServiceRegReqReject)
                .on(addServiceRegReq.addSvcRegReqId.eq(addServiceRegReqReject.addSvcRegReqId))
                .where(builder)
                .orderBy(addServiceRegReq.regiDateTime.desc());

        query.offset(pageable.getOffset())
        .limit(pageable.getPageSize());

        List<AddServiceRegReq> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }
}
