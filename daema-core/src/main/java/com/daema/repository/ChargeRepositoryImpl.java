package com.daema.repository;

import com.daema.domain.Charge;
import com.daema.domain.QCharge;
import com.daema.domain.attr.NetworkAttribute;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ChargeRepositoryImpl extends QuerydslRepositorySupport implements CustomChargeRepository {

    public ChargeRepositoryImpl() {
        super(Charge.class);
    }

    @Override
    public Page<Charge> getSearchPage(Pageable pageable, boolean isAdmin) {

        QCharge charge = QCharge.charge;

        JPQLQuery<Charge> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(charge.delYn.eq("N"));

        if(!isAdmin){
            builder.and(charge.useYn.eq("Y"));
        }

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
                ,Projections.fields(NetworkAttribute.class
                        ,charge.networkAttribute.telecom
                        ,charge.networkAttribute.network
                    ).as("networkAttribute")
                )
        );

        query.from(charge)
                .where(builder)
                .groupBy(charge.chargeId)
                .orderBy(charge.regiDateTime.desc());

        query.offset(pageable.getOffset())
        .limit(pageable.getPageSize());

        List<Charge> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public List<Charge> getMatchList() {

        QCharge charge = QCharge.charge;

        JPQLQuery<Charge> query = from(charge);

        query.select(Projections.fields(
                Charge.class
                ,charge.chargeId
                ,charge.chargeName
                ,charge.chargeAmt
                ,charge.category
                ,charge.useYn
                ,charge.matchingYn
                ,charge.regiDateTime
                ,Projections.fields(NetworkAttribute.class
                        ,charge.networkAttribute.telecom
                        ,charge.networkAttribute.network
                    ).as("networkAttribute")
                )
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
}
