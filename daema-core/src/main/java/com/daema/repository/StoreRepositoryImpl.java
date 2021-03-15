package com.daema.repository;

import com.daema.domain.QStore;
import com.daema.domain.QStoreMap;
import com.daema.domain.Store;
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

        JPQLQuery<Store> query = from(store);

        query.innerJoin(storeMap)
                .on(store.storeId.eq(storeMap.storeId)
                        .and(storeMap.parentStoreId.eq(1L))
                        .and(storeMap.useYn.eq("Y"))
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
                .on(store.storeId.eq(storeMap.storeId)
                        .and(storeMap.parentStoreId.eq(parentStoreId)
                        .and(store.storeId.eq(storeId)))
                );

        Store result = query.fetchOne();

        return result;
    }
}
