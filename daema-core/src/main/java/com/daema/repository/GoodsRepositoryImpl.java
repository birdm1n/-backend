package com.daema.repository;

import com.daema.domain.Goods;
import com.daema.domain.QGoods;
import com.daema.domain.QGoodsOption;
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

public class GoodsRepositoryImpl extends QuerydslRepositorySupport implements CustomGoodsRepository {

    public GoodsRepositoryImpl() {
        super(Goods.class);
    }

    @Override
    public Page<Goods> getSearchPage(Pageable pageable, boolean isAdmin) {

        QGoods goods = QGoods.goods;
        QGoodsOption goodsOption = QGoodsOption.goodsOption;

        JPQLQuery<Goods> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(goods.delYn.eq("N"));

        if(!isAdmin){
            builder.and(goods.useYn.eq("Y"));
        }

        query.select(Projections.fields(
                Goods.class
                ,goods.goodsId
                ,goods.goodsName
                ,goods.modelName
                ,goods.maker
                ,goods.capacity
                ,goods.originKey
                ,goods.useYn
                ,goods.matchingYn
                ,goods.delYn
                ,goods.regiDateTime
                ,Projections.fields(NetworkAttribute.class
                        ,goods.networkAttribute.telecom
                        ,goods.networkAttribute.network
                    ).as("networkAttribute")
                )
        );

        query.from(goods)
                .leftJoin(goodsOption)
                .on(goods.goodsId.eq(goodsOption.goodsId))
                .where(builder)
                .groupBy(goods.goodsId)
                .orderBy(goods.regiDateTime.desc());

        query.offset(pageable.getOffset())
        .limit(pageable.getPageSize());

        List<Goods> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public List<Goods> getMatchList() {

        QGoods goods = QGoods.goods;

        JPQLQuery<Goods> query = from(goods);

        query.select(Projections.fields(
                Goods.class
                ,goods.goodsId
                ,goods.goodsName
                ,goods.modelName
                ,goods.maker
                ,goods.originKey
                ,goods.useYn
                ,goods.matchingYn
                ,goods.regiDateTime
                ,Projections.fields(NetworkAttribute.class
                        ,goods.networkAttribute.telecom
                        ,goods.networkAttribute.network
                    ).as("networkAttribute")
                )
        );

        BooleanBuilder where = new BooleanBuilder();
        where.and(goods.matchingYn.eq("N")
                .and(goods.delYn.eq("N")));

        BooleanBuilder where2 = new BooleanBuilder();
        where2.and(goods.matchingYn.eq("Y")
                .and(goods.useYn.eq("Y"))
                .and(goods.delYn.eq("N")));

        query.where(where.or(Expressions.predicate(Ops.WRAPPED, where2)));

        query.orderBy(
                goods.networkAttribute.telecom.asc()
                ,goods.networkAttribute.network.asc()
                ,goods.maker.asc()
                ,goods.goodsName.asc()
                ,goods.useYn.desc()
        );

        return query.fetch();
    }
}
