package com.daema.commgmt.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.attr.NetworkAttribute;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.domain.dto.response.GoodsMatchRespDto;
import com.daema.commgmt.repository.custom.CustomGoodsRepository;
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

import static com.daema.commgmt.domain.QGoods.goods;
import static com.daema.wms.domain.QDevice.device;
import static com.daema.commgmt.domain.QGoodsOption.goodsOption;

public class GoodsRepositoryImpl extends QuerydslRepositorySupport implements CustomGoodsRepository {

    public GoodsRepositoryImpl() {
        super(Goods.class);
    }

    @Override
    public Page<Goods> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin) {

        JPQLQuery<Goods> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(goods.delYn.eq("N"));

        if (!isAdmin) {
            builder.and(goods.useYn.eq("Y"));
        }

        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail network = new QCodeDetail("network");
        QCodeDetail maker = new QCodeDetail("maker");

        query.select(Projections.fields(
                Goods.class
                , goods.goodsId
                , goods.goodsName
                , goods.modelName
                , goods.maker
                , goods.capacity
                , goods.originKey
                , goods.useYn
                , goods.matchingYn
                , goods.delYn
                , goods.regiDateTime
                , maker.codeNm.as("makerName")
                , telecom.codeNm.as("telecomName")
                , network.codeNm.as("networkName")
                , Projections.fields(NetworkAttribute.class
                        , goods.networkAttribute.telecom
                        , goods.networkAttribute.network
                ).as("networkAttribute")
                )
        );

        query.from(goods)
                .innerJoin(maker)
                .on(goods.maker.eq(maker.codeSeq)
                        .and(maker.codeId.eq("MAKER"))
                        .and(maker.useYn.eq("Y"))
                )
                .innerJoin(telecom)
                .on(goods.networkAttribute.telecom.eq(telecom.codeSeq)
                        .and(telecom.codeId.eq("TELECOM"))
                        .and(telecom.useYn.eq("Y"))
                )
                .innerJoin(network)
                .on(goods.networkAttribute.network.eq(network.codeSeq)
                        .and(network.codeId.eq("NETWORK"))
                        .and(network.useYn.eq("Y"))
                )
                .where(
                        builder
                        , containsGoodsName(requestDto.getGoodsName())
                        , containsModelName(requestDto.getModelName())
                        , eqMaker(requestDto.getMaker())
                        , eqNetwork(requestDto.getNetwork())
                        , eqTelecom(requestDto.getTelecom())
                        , eqUseYn(requestDto.getUseYn())
                        , eqMatchingYn(requestDto.getMatchingYn())
                        , betweenStartDateEndDate(requestDto.getSrhStartDate(), requestDto.getSrhEndDate())
                )
                .orderBy(goods.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<Goods> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public List<Goods> getMatchList(ComMgmtRequestDto requestDto) {

        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail network = new QCodeDetail("network");
        QCodeDetail maker = new QCodeDetail("maker");

        JPQLQuery<Goods> query = from(goods);

        query.select(Projections.fields(
                Goods.class
                , goods.goodsId
                , goods.goodsName
                , goods.modelName
                , goods.maker
                , goods.originKey
                , goods.useYn
                , goods.matchingYn
                , goods.regiDateTime
                , maker.codeNm.as("makerName")
                , telecom.codeNm.as("telecomName")
                , network.codeNm.as("networkName")
                , Projections.fields(NetworkAttribute.class
                        , goods.networkAttribute.telecom
                        , goods.networkAttribute.network
                ).as("networkAttribute")
                )
        );

        query.innerJoin(maker)
                .on(goods.maker.eq(maker.codeSeq)
                        .and(maker.codeId.eq("MAKER"))
                        .and(maker.useYn.eq("Y"))
                )
                .innerJoin(telecom)
                .on(goods.networkAttribute.telecom.eq(telecom.codeSeq)
                        .and(telecom.codeId.eq("TELECOM"))
                        .and(telecom.useYn.eq("Y"))
                )
                .innerJoin(network)
                .on(goods.networkAttribute.network.eq(network.codeSeq)
                        .and(network.codeId.eq("NETWORK"))
                        .and(network.useYn.eq("Y"))
                );

        BooleanBuilder where = new BooleanBuilder();
        where.and(goods.matchingYn.eq("N")
                .and(goods.delYn.eq("N")));

        BooleanBuilder where2 = new BooleanBuilder();
        where2.and(goods.matchingYn.eq("Y")
                .and(goods.useYn.eq("Y"))
                .and(goods.delYn.eq("N")));

        query.where(
                eqMaker(requestDto.getMaker())
                , eqNetwork(requestDto.getNetwork())
                , eqTelecom(requestDto.getTelecom())
                , where.or(Expressions.predicate(Ops.WRAPPED, where2)));

        query.orderBy(
                goods.networkAttribute.telecom.asc()
                , goods.networkAttribute.network.asc()
                , goods.maker.asc()
                , goods.goodsName.asc()
                , goods.useYn.desc()
        );
        return query.fetch();
    }

    @Override
    public GoodsMatchRespDto goodsMatchBarcode(String commonBarcode) {
        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail maker = new QCodeDetail("maker");

        JPQLQuery<Goods> query = getQuerydsl().createQuery();

        GoodsMatchRespDto goodsMatchRespDto =
                query.select(Projections.fields(
                        GoodsMatchRespDto.class
                        , maker.codeSeq.as("maker")
                        , maker.codeNm.as("makerName")
                        , telecom.codeSeq.as("telecom")
                        , telecom.codeNm.as("telecomName")
                        , goods.goodsId.as("goodsId")
                        , goods.goodsName.as("goodsName")
                        , goods.modelName.as("modelName")
                        , goods.capacity.as("capacity")
                        , goodsOption.goodsOptionId.as("goodsOptionId")
                        , goodsOption.colorName.as("colorName")
                        , goodsOption.commonBarcode.as("commonBarcode")
                )).from(goods)
                        .innerJoin(goodsOption)
                        .on(
                                goodsOption.commonBarcode.eq(commonBarcode),
                                goods.goodsId.eq(goodsOption.goods.goodsId)
                        )
                        .innerJoin(maker)
                        .on(
                                goods.maker.eq(maker.codeSeq),
                                maker.useYn.eq("Y")
                        )
                        .innerJoin(telecom)
                        .on(
                                goods.networkAttribute.telecom.eq(telecom.codeSeq),
                                telecom.useYn.eq("Y")
                        ).fetchOne();


        return goodsMatchRespDto;
    }

    @Override
    public List<Goods> getCapacityList(Long storeId) {
        JPQLQuery<Goods> query = getQuerydsl().createQuery();
        return query.select(Projections.fields(
                Goods.class
                , goods.capacity
        ))
                .distinct()
                .from(device)
                .innerJoin(device.goodsOption, goodsOption).on(
                        device.store.storeId.eq(storeId),
                        device.delYn.eq("N"),
                        goodsOption.delYn.eq("N")
                )
                .innerJoin(goodsOption.goods, goods).on(
                        goods.delYn.eq("N")
                )
                .orderBy(goods.capacity.asc())
                .fetch();
    }

    @Override
    public List<GoodsOption> getColorList(long goodsId) {
        JPQLQuery<GoodsOption> query = getQuerydsl().createQuery();
        return query.select(Projections.fields(
                GoodsOption.class
                , goodsOption.goodsOptionId
                , goodsOption.colorName
        ))
                .from(goodsOption)
                .where(
                        goodsOption.goods.goodsId.eq(goodsId),
                        goodsOption.delYn.eq("N")
                )
                .orderBy(goodsOption.colorName.asc())
                .fetch();
    }

    private BooleanExpression containsGoodsName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return goods.goodsName.contains(name);
    }

    private BooleanExpression containsModelName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return goods.modelName.contains(name);
    }

    private BooleanExpression eqMaker(int name) {
        if (name <= 0) {
            return null;
        }
        return goods.maker.eq(name);
    }

    private BooleanExpression eqNetwork(int name) {
        if (name <= 0) {
            return null;
        }
        return goods.networkAttribute.network.eq(name);
    }

    private BooleanExpression eqTelecom(Integer[] name) {
        if (name == null
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)
                || (name != null && name.length <= 0)) {
            return null;
        }
        return goods.networkAttribute.telecom.in(name);
    }

    private BooleanExpression eqUseYn(String name) {
        if (StringUtils.isEmpty(name) || "all".equals(name)) {
            return null;
        }
        return goods.useYn.eq(name);
    }

    private BooleanExpression betweenStartDateEndDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)
                || StringUtils.isEmpty(endDate)) {
            return null;
        }
        return goods.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate, "s")
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate, "e"));
    }

    private BooleanExpression eqMatchingYn(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return goods.matchingYn.eq(name);
    }
}
