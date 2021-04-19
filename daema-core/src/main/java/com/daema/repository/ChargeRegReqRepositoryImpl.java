package com.daema.repository;

import com.daema.domain.ChargeRegReq;
import com.daema.domain.ChargeRegReqReject;
import com.daema.domain.QCodeDetail;
import com.daema.domain.QStore;
import com.daema.domain.attr.NetworkAttribute;
import com.daema.domain.common.RetrieveClauseBuilder;
import com.daema.domain.dto.common.SearchParamDto;
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

import static com.daema.domain.QChargeRegReq.chargeRegReq;
import static com.daema.domain.QChargeRegReqReject.chargeRegReqReject;

public class ChargeRegReqRepositoryImpl extends QuerydslRepositorySupport implements CustomChargeRegReqRepository {

    public ChargeRegReqRepositoryImpl() {
        super(ChargeRegReq.class);
    }

    @Override
    public Page<ChargeRegReq> getSearchPage(SearchParamDto requestDto, boolean isAdmin) {

        JPQLQuery<ChargeRegReq> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();

        if(!isAdmin){
            builder.and(chargeRegReq.reqStoreId.eq(requestDto.getStoreId()));
        }

        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail network = new QCodeDetail("network");
        QStore store = QStore.store;

        query.select(Projections.fields(
                ChargeRegReq.class
                ,chargeRegReq.chargeRegReqId
                ,chargeRegReq.category
                ,chargeRegReq.chargeName
                ,chargeRegReq.chargeAmt
                ,chargeRegReq.reqStatus
                ,chargeRegReq.reqStoreId
                ,chargeRegReq.regiDateTime
                ,telecom.codeNm.as("telecomName")
                ,network.codeNm.as("networkName")
                ,store.storeName.as("reqStoreName")
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
                .innerJoin(telecom)
                .on(chargeRegReq.networkAttribute.telecom.eq(telecom.codeSeq)
                        .and(telecom.codeId.eq("TELECOM"))
                        .and(telecom.useYn.eq("Y"))
                )
                .innerJoin(network)
                .on(chargeRegReq.networkAttribute.network.eq(network.codeSeq)
                        .and(network.codeId.eq("NETWORK"))
                        .and(network.useYn.eq("Y"))
                )
                .innerJoin(store)
                .on(chargeRegReq.reqStoreId.eq(store.storeId))
                .leftJoin(chargeRegReqReject)
                .on(chargeRegReq.chargeRegReqId.eq(chargeRegReqReject.chargeRegReqId))
                .where(
                        builder
                        ,containsChargeName(requestDto.getChargeName())
                        ,eqNetwork(requestDto.getNetwork())
                        ,eqTelecom(requestDto.getTelecom())
                        ,eqStatus(requestDto.getReqStatus())
                        ,betweenStartDateEndDate(requestDto.getSrhStartDate(), requestDto.getSrhEndDate())
                )
                .orderBy(chargeRegReq.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<ChargeRegReq> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression containsChargeName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return chargeRegReq.chargeName.contains(name);
    }

    private BooleanExpression eqNetwork(int name) {
        if (name <= 0) {
            return null;
        }
        return chargeRegReq.networkAttribute.network.eq(name);
    }

    private BooleanExpression eqTelecom(Integer[] name) {
        if (name == null
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)) {
            return null;
        }
        return chargeRegReq.networkAttribute.telecom.in(name);
    }

    private BooleanExpression eqStatus(int name) {
        if (name <= 0) {
            return null;
        }
        return chargeRegReq.reqStatus.eq(name);
    }

    private BooleanExpression betweenStartDateEndDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)
                || StringUtils.isEmpty(endDate)) {
            return null;
        }
        return chargeRegReq.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate.concat(" 00:00:00"))
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate.concat(" 23:59:59")));
    }
}
