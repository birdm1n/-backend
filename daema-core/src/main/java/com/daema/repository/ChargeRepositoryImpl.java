package com.daema.repository;

import com.daema.domain.Charge;
import com.daema.domain.QCharge;
import com.daema.domain.QCodeDetail;
import com.daema.domain.attr.NetworkAttribute;
import com.daema.domain.common.RetrieveClauseBuilder;
import com.daema.domain.dto.common.SearchParamDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.daema.domain.QCharge.charge;

public class ChargeRepositoryImpl extends QuerydslRepositorySupport implements CustomChargeRepository {

    public ChargeRepositoryImpl() {
        super(Charge.class);
    }

    @Override
    public Page<Charge> getSearchPage(SearchParamDto requestDto, boolean isAdmin) {

        JPQLQuery<Charge> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(charge.delYn.eq("N"));

        if(!isAdmin){
            builder.and(charge.useYn.eq("Y"));
        }

        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail network = new QCodeDetail("network");

        query.select(Projections.fields(
                Charge.class
                ,charge.chargeId
                ,charge.chargeName
                ,charge.chargeAmt
                ,charge.category
                ,charge.originKey
                ,charge.useYn
                ,charge.matchingYn
                ,charge.delYn
                ,charge.regiDateTime
                ,telecom.codeNm.as("telecomName")
                ,network.codeNm.as("networkName")
                ,Projections.fields(NetworkAttribute.class
                        ,charge.networkAttribute.telecom
                        ,charge.networkAttribute.network
                    ).as("networkAttribute")
                )
        );

        query.from(charge)
                .innerJoin(telecom)
                .on(charge.networkAttribute.telecom.eq(telecom.codeSeq)
                        .and(telecom.codeId.eq("TELECOM"))
                        .and(telecom.useYn.eq("Y"))
                )
                .innerJoin(network)
                .on(charge.networkAttribute.network.eq(network.codeSeq)
                        .and(network.codeId.eq("NETWORK"))
                        .and(network.useYn.eq("Y"))
                )
                .where(
                        builder
                        ,containsChargeName(requestDto.getChargeName())
                        ,eqNetwork(requestDto.getNetwork())
                        ,eqTelecom(requestDto.getTelecom())
                        ,eqUseYn(requestDto.getUseYn())
                        ,eqMatchingYn(requestDto.getMatchingYn())
                        ,betweenStartDateEndDate(requestDto.getSrhStartDate(), requestDto.getSrhEndDate())
                )
                .orderBy(charge.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<Charge> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public List<Charge> getMatchList() {

        QCharge charge = QCharge.charge;
        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail network = new QCodeDetail("network");

        JPQLQuery<Charge> query = from(charge);

        query.select(Projections.fields(
                Charge.class
                ,charge.chargeId
                ,charge.chargeName
                ,charge.chargeAmt
                ,charge.category
                ,charge.originKey
                ,charge.useYn
                ,charge.matchingYn
                ,charge.regiDateTime
                ,telecom.codeNm.as("telecomName")
                ,network.codeNm.as("networkName")
                ,Projections.fields(NetworkAttribute.class
                        ,charge.networkAttribute.telecom
                        ,charge.networkAttribute.network
                    ).as("networkAttribute")
                )
        );

        query.innerJoin(telecom)
                .on(charge.networkAttribute.telecom.eq(telecom.codeSeq)
                        .and(telecom.codeId.eq("TELECOM"))
                        .and(telecom.useYn.eq("Y"))
                )
                .innerJoin(network)
                .on(charge.networkAttribute.network.eq(network.codeSeq)
                        .and(network.codeId.eq("NETWORK"))
                        .and(network.useYn.eq("Y"))
                );

        BooleanBuilder where = new BooleanBuilder();
        where.and(charge.matchingYn.eq("N")
                .and(charge.delYn.eq("N")));

        BooleanBuilder where2 = new BooleanBuilder();
        where2.and(charge.matchingYn.eq("Y")
                .and(charge.useYn.eq("Y"))
                .and(charge.delYn.eq("N")));

        query.where(where.or(Expressions.predicate(Ops.WRAPPED, where2)));

        query.orderBy(
                charge.networkAttribute.telecom.asc()
                ,charge.networkAttribute.network.asc()
                ,charge.chargeName.asc()
                ,charge.useYn.desc()
        );

        return query.fetch();
    }

    private BooleanExpression containsChargeName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return charge.chargeName.contains(name);
    }

    private BooleanExpression eqNetwork(int name) {
        if (name <= 0) {
            return null;
        }
        return charge.networkAttribute.network.eq(name);
    }

    private BooleanExpression eqTelecom(Integer[] name) {
        if (name == null
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)
                || (name != null && name.length <= 0)) {
            return null;
        }
        return charge.networkAttribute.telecom.in(name);
    }

    private BooleanExpression eqUseYn(String name) {
        if (StringUtils.isEmpty(name) || "all".equals(name)) {
            return null;
        }
        return charge.useYn.eq(name);
    }

    private BooleanExpression betweenStartDateEndDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)
                || StringUtils.isEmpty(endDate)) {
            return null;
        }
        return charge.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate.concat(" 00:00:00"))
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate.concat(" 23:59:59")));
    }

    private BooleanExpression eqMatchingYn(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return charge.matchingYn.eq(name);
    }
}
