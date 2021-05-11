package com.daema.commgmt.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.base.enums.StatusEnum;
import com.daema.commgmt.domain.ChargeRegReq;
import com.daema.commgmt.domain.ChargeRegReqReject;
import com.daema.commgmt.domain.QStore;
import com.daema.commgmt.domain.attr.NetworkAttribute;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.repository.custom.CustomChargeRegReqRepository;
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

import static com.daema.commgmt.domain.QChargeRegReq.chargeRegReq;
import static com.daema.commgmt.domain.QChargeRegReqReject.chargeRegReqReject;

public class ChargeRegReqRepositoryImpl extends QuerydslRepositorySupport implements CustomChargeRegReqRepository {

    public ChargeRegReqRepositoryImpl() {
        super(ChargeRegReq.class);
    }

    @Override
    public Page<ChargeRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin) {

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
                        .and(telecom.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
                )
                .innerJoin(network)
                .on(chargeRegReq.networkAttribute.network.eq(network.codeSeq)
                        .and(network.codeId.eq("NETWORK"))
                        .and(network.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
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
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)
                || (name != null && name.length <= 0)) {
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
        return chargeRegReq.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate, "s")
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate, "e"));
    }
}
