package com.daema.repository;

import com.daema.domain.QStore;
import com.daema.domain.QStoreMap;
import com.daema.domain.Store;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StoreRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreRepository {

    public StoreRepositoryImpl() {
        super(Store.class);
    }

    @Override
    public Page<Store> getSearchPage(Pageable pageable) {

        QStore store = QStore.store;
        QStoreMap storeMap = QStoreMap.storeMap;

        JPQLQuery<Store> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                Store.class
                ,store.storeId
                ,store.storeName
                ,store.telecom
                ,store.bizNo
                ,store.chargerPhone
                ,store.returnZipCode
                ,store.returnAddr
                ,store.returnAddrDetail
                ,store.regiDateTime
                ,storeMap.useYn.as("useYn")
        ));

        query.from(store);

        //TODO 하드코딩
        query.innerJoin(storeMap)
            .on(store.storeId.eq(storeMap.storeId)
                .and(storeMap.parentStoreId.eq(1L))
            )
            .orderBy(store.regiDateTime.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        List<Store> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public Store findStoreInfo(long parentStoreId, long storeId) {

        QStore store = QStore.store;
        QStoreMap storeMap = QStoreMap.storeMap;

        JPQLQuery<Store> query = from(store);

        query.innerJoin(storeMap)
                .on(buildOnQuery(store, storeMap, parentStoreId, storeId));

        Store result = query.fetchOne();

        return result;
    }

    private BooleanBuilder buildOnQuery(QStore store, QStoreMap storeMap, long parentStoreId, long storeId){
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(store.storeId.eq(storeMap.storeId));

        if(parentStoreId > 0){
            builder.and(eqParentStoreId(storeMap, parentStoreId));
        }

        builder.and(store.storeId.eq(storeId));

        return builder;
    }

    private BooleanExpression eqParentStoreId(QStoreMap storeMap, long parentStoreId){
        if(parentStoreId <= 0){
            return null;
        }
        return storeMap.parentStoreId.eq(parentStoreId);
    }

    @Override
    public List<Store> findBySaleStore(long parentStoreId, OrderSpecifier orderSpecifier) {
        QStoreMap storeMap = QStoreMap.storeMap;
        QStore store = QStore.store;

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
