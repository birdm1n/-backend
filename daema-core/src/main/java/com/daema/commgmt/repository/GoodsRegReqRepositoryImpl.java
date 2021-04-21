package com.daema.commgmt.repository;

import com.daema.commgmt.domain.GoodsRegReq;
import com.daema.commgmt.domain.GoodsRegReqReject;
import com.daema.base.domain.QCodeDetail;
import com.daema.commgmt.domain.QStore;
import com.daema.commgmt.domain.attr.NetworkAttribute;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.repository.custom.CustomGoodsRegReqRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.daema.commgmt.domain.QGoodsRegReq.goodsRegReq;
import static com.daema.commgmt.domain.QGoodsRegReqReject.goodsRegReqReject;

public class GoodsRegReqRepositoryImpl extends QuerydslRepositorySupport implements CustomGoodsRegReqRepository {

    public GoodsRegReqRepositoryImpl() {
        super(GoodsRegReq.class);
    }

    @Override
    public Page<GoodsRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin) {

        JPQLQuery<GoodsRegReq> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();

        if(!isAdmin){
            builder.and(goodsRegReq.reqStoreId.eq(requestDto.getStoreId()));
        }

        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail network = new QCodeDetail("network");
        QCodeDetail maker = new QCodeDetail("maker");
        QStore store = QStore.store;

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
                ,maker.codeNm.as("makerName")
                ,telecom.codeNm.as("telecomName")
                ,network.codeNm.as("networkName")
                ,store.storeName.as("reqStoreName")
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
                .innerJoin(maker)
                .on(goodsRegReq.maker.eq(maker.codeSeq)
                        .and(maker.codeId.eq("MAKER"))
                        .and(maker.useYn.eq("Y"))
                )
                .innerJoin(telecom)
                .on(goodsRegReq.networkAttribute.telecom.eq(telecom.codeSeq)
                        .and(telecom.codeId.eq("TELECOM"))
                        .and(telecom.useYn.eq("Y"))
                )
                .innerJoin(network)
                .on(goodsRegReq.networkAttribute.network.eq(network.codeSeq)
                        .and(network.codeId.eq("NETWORK"))
                        .and(network.useYn.eq("Y"))
                )
                .innerJoin(store)
                .on(goodsRegReq.reqStoreId.eq(store.storeId))
                .leftJoin(goodsRegReqReject)
                .on(goodsRegReq.goodsRegReqId.eq(goodsRegReqReject.goodsRegReqId))
                .where(
                        builder
                        ,containsGoodsName(requestDto.getGoodsName())
                        ,containsModelName(requestDto.getModelName())
                        ,eqMaker(requestDto.getMaker())
                        ,eqNetwork(requestDto.getNetwork())
                        ,eqTelecom(requestDto.getTelecom())
                        ,eqStatus(requestDto.getReqStatus())
                        ,betweenStartDateEndDate(requestDto.getSrhStartDate(), requestDto.getSrhEndDate())
                )
                .orderBy(goodsRegReq.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<GoodsRegReq> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression containsGoodsName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return goodsRegReq.goodsName.contains(name);
    }

    private BooleanExpression containsModelName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return goodsRegReq.modelName.contains(name);
    }

    private BooleanExpression eqMaker(int name) {
        if (name <= 0) {
            return null;
        }
        return goodsRegReq.maker.eq(name);
    }

    private BooleanExpression eqNetwork(int name) {
        if (name <= 0) {
            return null;
        }
        return goodsRegReq.networkAttribute.network.eq(name);
    }

    private BooleanExpression eqTelecom(Integer[] name) {
        if (name == null
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)
                || (name != null && name.length <= 0)) {
            return null;
        }
        return goodsRegReq.networkAttribute.telecom.in(name);
    }

    private BooleanExpression eqStatus(int name) {
        if (name <= 0) {
            return null;
        }
        return goodsRegReq.reqStatus.eq(name);
    }

    private BooleanExpression betweenStartDateEndDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)
                || StringUtils.isEmpty(endDate)) {
            return null;
        }
        return goodsRegReq.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate, "s")
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate, "e"));
    }
}
