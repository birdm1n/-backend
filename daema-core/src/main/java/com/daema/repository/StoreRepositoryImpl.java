package com.daema.repository;

import com.daema.domain.Store;
import com.daema.domain.common.RetrieveClauseBuilder;
import com.daema.domain.dto.common.SearchParamDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
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

import static com.daema.domain.QStore.store;
import static com.daema.domain.QStoreMap.storeMap;
import static com.daema.domain.QCodeDetail.codeDetail;

public class StoreRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreRepository {

    public StoreRepositoryImpl() {
        super(Store.class);
    }

    @Override
    public Page<Store> getSearchPage(SearchParamDto requestDto) {

        JPQLQuery<Store> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                Store.class
                , store.storeId
                , store.storeName
                , store.telecom
                , store.bizNo
                , store.chargerPhone
                , store.returnZipCode
                , store.returnAddr
                , store.returnAddrDetail
                , store.regiDateTime
                , storeMap.useYn.as("useYn")
                , codeDetail.codeNm.as("telecomName")
        ));

        query.from(store);

        query.innerJoin(storeMap)
                .on(store.storeId.eq(storeMap.storeId)
                        .and(storeMap.parentStoreId.eq(requestDto.getParentStoreId()))
                );

        query.innerJoin(codeDetail)
                .on(store.telecom.eq(codeDetail.codeSeq)
                    .and(codeDetail.codeId.eq("TELECOM"))
                    .and(codeDetail.useYn.eq("Y"))
                );

        query.where(
                containsSaleStoreName(requestDto.getSaleStoreName())
                ,containsBizNo(requestDto.getBizNo())
                ,containsChargerPhone(requestDto.getChargerPhone())
                ,containsReturnAddr(requestDto.getReturnAddr())
                ,inTelecom(requestDto.getTelecom())
                ,eqUseYn(requestDto.getUseYn())
                ,betweenStartDateEndDate(requestDto.getSrhStartDate(), requestDto.getSrhEndDate())
        ).orderBy(store.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<Store> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression containsSaleStoreName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return store.storeName.contains(name);
    }

    private BooleanExpression containsBizNo(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return store.bizNo.contains(name);
    }

    private BooleanExpression containsChargerPhone(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return store.chargerPhone.contains(name);
    }

    private BooleanExpression containsReturnAddr(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return store.returnAddr.contains(name).or(store.returnAddrDetail.contains(name));
    }

    private BooleanExpression inTelecom(Integer[] name) {
        if (name == null
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)) {
            return null;
        }
        return store.telecom.in(name);
    }

    private BooleanExpression eqUseYn(String name) {
        if (StringUtils.isEmpty(name) || "all".equals(name)) {
            return null;
        }
        return storeMap.useYn.eq(name);
    }

    private BooleanExpression betweenStartDateEndDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)
                || StringUtils.isEmpty(endDate)) {
            return null;
        }
        return store.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate.concat(" 00:00:00"))
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate.concat(" 23:59:59")));
    }

    @Override
    public Store findStoreInfo(long parentStoreId, long storeId) {

        JPQLQuery<Store> query = from(store);

        query.innerJoin(storeMap)
                .on(buildOnQuery(parentStoreId, storeId));

        Store result = query.fetchOne();

        return result;
    }

    private BooleanBuilder buildOnQuery(long parentStoreId, long storeId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(store.storeId.eq(storeMap.storeId));
        builder.and(storeMap.parentStoreId.eq(parentStoreId));
        builder.and(store.storeId.eq(storeId));

        return builder;
    }

    @Override
    public List<Store> findBySaleStore(long parentStoreId, OrderSpecifier orderSpecifier) {

        JPQLQuery<Store> query = from(store);

        query.innerJoin(storeMap)
                .on(storeMap.parentStoreId.eq(parentStoreId)
                        .and(store.storeId.eq(storeMap.storeId))
                        .and(store.useYn.eq("Y")
                                .and(storeMap.useYn.eq("Y")))
                );

        query.orderBy(orderSpecifier);

        return query.fetch();
    }
}
