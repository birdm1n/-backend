package com.daema.repository;

import com.daema.domain.GoodsRegReq;
import com.daema.domain.GoodsRegReqReject;
import com.daema.domain.QGoodsRegReq;
import com.daema.domain.QGoodsRegReqReject;
import com.daema.domain.attr.NetworkAttribute;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class GoodsRegReqRepositoryImpl extends QuerydslRepositorySupport implements CustomGoodsRegReqRepository {

    public GoodsRegReqRepositoryImpl() {
        super(GoodsRegReq.class);
    }

    @Override
    public Page<GoodsRegReq> getSearchPage(Pageable pageable, boolean isAdmin) {

        QGoodsRegReq goodsRegReq = QGoodsRegReq.goodsRegReq;
        QGoodsRegReqReject goodsRegReqReject = QGoodsRegReqReject.goodsRegReqReject;

        JPQLQuery<GoodsRegReq> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();

        if(!isAdmin){
            //TODO 하드코딩
            builder.and(goodsRegReq.reqStoreId.eq(1L));
        }

        query.select(Projections.fields(
                GoodsRegReq.class
                ,goodsRegReq.goodsRegReqId
                ,goodsRegReq.modelName
                ,goodsRegReq.maker
                ,goodsRegReq.capacity
                ,goodsRegReq.goodsName
                ,goodsRegReq.reqStatus
                ,goodsRegReq.reqStoreId
                ,goodsRegReq.regiDateTime
                ,Projections.fields(NetworkAttribute.class
                        ,goodsRegReq.networkAttribute.telecom
                        ,goodsRegReq.networkAttribute.network
                    ).as("networkAttribute")
                ,Projections.fields(GoodsRegReqReject.class
                        ,goodsRegReqReject.goodsRegReqId
                        ,goodsRegReqReject.rejectComment
                        ,goodsRegReqReject.rejectDateTime
                        ,goodsRegReqReject.rejectUserId
                ).as("goodsRegReqReject")
            )
        );

        query.from(goodsRegReq)
                .leftJoin(goodsRegReqReject)
                .on(goodsRegReq.goodsRegReqId.eq(goodsRegReqReject.goodsRegReqId))
                .where(builder)
                .orderBy(goodsRegReq.regiDateTime.desc());

        query.offset(pageable.getOffset())
        .limit(pageable.getPageSize());

        List<GoodsRegReq> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }
}
