package com.daema.wms.repository;

import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.ReturnStock;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import com.daema.wms.repository.custom.CustomReturnStockRepository;
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

import static com.daema.base.domain.QCodeDetail.codeDetail;
import static com.daema.commgmt.domain.QStore.store;
import static com.daema.commgmt.domain.QStoreMap.storeMap;

public class ReturnStockRepositoryImpl extends QuerydslRepositorySupport implements CustomReturnStockRepository {

    public ReturnStockRepositoryImpl() {
        super(ReturnStock.class);
    }

    @Override
    public Page<ReturnStock> getSearchPage(ReturnStockRequestDto requestDto) {

        JPQLQuery<ReturnStock> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                Store.class
                , store.storeId
                , store.storeName
                , store.telecom
                , store.bizNo
                , store.ceoName
                , store.chargerPhone
                , store.chargerName
                , store.chargerEmail
                , store.returnZipCode
                , store.returnAddr
                , store.returnAddrDetail
                , store.regiDateTime
                , storeMap.useYn.as("useYn")
                , codeDetail.codeNm.as("telecomName")
        ));
/*
        query.from(store);

        query.innerJoin(storeMap)
                .on(store.storeId.eq(storeMap.storeId)
                        .and(storeMap.parentStoreId.eq(requestDto.getParentStoreId()))
                        .and(store.useYn.eq("Y"))
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
        ).orderBy(store.regiDateTime.desc());*/

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<ReturnStock> resultList = query.fetch();

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
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)
                || (name != null && name.length <= 0)) {
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
        return store.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate, "s")
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate, "e"));
    }
}
