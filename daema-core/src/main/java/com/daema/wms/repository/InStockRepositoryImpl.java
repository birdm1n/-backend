package com.daema.wms.repository;

import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import com.daema.wms.repository.custom.CustomInStockRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.daema.wms.domain.QInStockWait.inStockWait;
import static com.daema.wms.domain.QProvider.provider;
import static com.daema.commgmt.domain.QStore.store;

public class InStockRepositoryImpl extends QuerydslRepositorySupport implements CustomInStockRepository {

    public InStockRepositoryImpl() {
        super(InStock.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<InStockWaitDto> getWaitInStockList(InStockRequestDto requestDto) {
        JPQLQuery<InStockWaitDto> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                InStockWaitDto.class
//                , inStockWait.waitId.as("waitId")
                , inStockWait.regiDateTime.as("regiDateTime")
//            ,inStockWait.regiUserId.as("regiUserId")
//            ,inStockWait.updDateTime.as("updDateTime")
//            ,inStockWait.updUserId.as("updUserId")
//            ,inStockWait.delYn.as("delYn")
//            ,inStockWait.waitId.as("waitId")
//            ,inStockWait.inStockType.as("inStockType")
//            ,inStockWait.inStockStatus.as("inStockStatus")
//            ,inStockWait.inStockAmt.as("inStockAmt")
//            ,inStockWait.inStockMemo.as("inStockMemo")
//            ,inStockWait.productFaultyYn.as("productFaultyYn")
//            ,inStockWait.extrrStatus.as("extrrStatus")
//            ,inStockWait.productMissYn.as("productMissYn")
//            ,inStockWait.missProduct.as("missProduct")
//            ,inStockWait.ddctAmt.as("ddctAmt")
//            ,inStockWait.ownStoreId.as("ownStoreId")
//            ,inStockWait.holdStoreId.as("holdStoreId")
//            ,inStockWait.goodsOptionId.as("goodsOptionId")
//            ,inStockWait.goodsId.as("goodsId")
        ));

        return query.from(inStockWait)
                .leftJoin(provider).on(inStockWait.provId.eq(provider.provId))
                .leftJoin(store).on(inStockWait.ownStoreId.eq(store.storeId))
                .leftJoin(store).on(inStockWait.ownStoreId.eq(store.storeId))
                .fetch();
    }
}
