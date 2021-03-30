package com.daema.repository;

import com.daema.domain.ChargeRegReq;
import com.daema.domain.ChargeRegReqReject;
import com.daema.domain.QChargeRegReq;
import com.daema.domain.QChargeRegReqReject;
import com.daema.domain.attr.NetworkAttribute;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ChargeRegReqRepositoryImpl extends QuerydslRepositorySupport implements CustomChargeRegReqRepository {

    public ChargeRegReqRepositoryImpl() {
        super(ChargeRegReq.class);
    }

    @Override
    public Page<ChargeRegReq> getSearchPage(Pageable pageable, boolean isAdmin) {

        QChargeRegReq chargeRegReq = QChargeRegReq.chargeRegReq;
        QChargeRegReqReject chargeRegReqReject = QChargeRegReqReject.chargeRegReqReject;

        JPQLQuery<ChargeRegReq> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();

        if(!isAdmin){
            //TODO 하드코딩
            builder.and(chargeRegReq.reqStoreId.eq(1L));
        }

        query.select(Projections.fields(
                ChargeRegReq.class
                ,chargeRegReq.chargeRegReqId
                ,chargeRegReq.category
                ,chargeRegReq.chargeName
                ,chargeRegReq.chargeAmt
                ,chargeRegReq.reqStatus
                ,chargeRegReq.reqStoreId
                ,chargeRegReq.regiDateTime
                ,Projections.fields(NetworkAttribute.class
                        ,chargeRegReq.networkAttribute.telecom
                        ,chargeRegReq.networkAttribute.network
                    ).as("networkAttribute")
                ,Projections.fields(ChargeRegReqReject.class
                        ,chargeRegReqReject.chargeRegReqId
                        ,chargeRegReqReject.rejectComment
                        ,chargeRegReqReject.rejectDateTime
                        ,chargeRegReqReject.rejectUserId
                ).as("chargeRegReqReject")
            )
        );

        query.from(chargeRegReq)
                .leftJoin(chargeRegReqReject)
                .on(chargeRegReq.chargeRegReqId.eq(chargeRegReqReject.chargeRegReqId))
                .where(builder)
                .orderBy(chargeRegReq.regiDateTime.desc());

        query.offset(pageable.getOffset())
        .limit(pageable.getPageSize());

        List<ChargeRegReq> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }
}
