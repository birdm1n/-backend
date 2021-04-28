package com.daema.wms.repository;

import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.WaitInStockDto;
import com.daema.wms.repository.custom.CustomInStockRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.daema.wms.domain.QWaitInStock.waitInStock;
import static com.daema.wms.domain.QProvider.provider;
import static com.daema.wms.domain.QDevice.device;
import static com.daema.commgmt.domain.QStore.store;
import static com.daema.commgmt.domain.QGoods.goods;
import static com.daema.commgmt.domain.QGoodsOption.goodsOption;

public class InStockRepositoryImpl extends QuerydslRepositorySupport implements CustomInStockRepository {

    public InStockRepositoryImpl() {
        super(InStock.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<WaitInStockDto> getWaitInStockList(InStockRequestDto requestDto) {
        JPQLQuery<WaitInStockDto> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                WaitInStockDto.class
                , waitInStock.waitId.as("waitId")
                , waitInStock.regiDateTime.as("regiDateTime")
//            ,waitInStock.regiUserId.as("regiUserId")
//            ,waitInStock.updDateTime.as("updDateTime")
//            ,waitInStock.updUserId.as("updUserId")
//            ,waitInStock.delYn.as("delYn")
//            ,waitInStock.waitId.as("waitId")
//            ,waitInStock.inStockType.as("inStockType")
//            ,waitInStock.inStockStatus.as("inStockStatus")
//            ,waitInStock.inStockAmt.as("inStockAmt")
//            ,waitInStock.inStockMemo.as("inStockMemo")
//            ,waitInStock.productFaultyYn.as("productFaultyYn")
//            ,waitInStock.extrrStatus.as("extrrStatus")
//            ,waitInStock.productMissYn.as("productMissYn")
//            ,waitInStock.missProduct.as("missProduct")
//            ,waitInStock.ddctAmt.as("ddctAmt")
//            ,waitInStock.ownStoreId.as("ownStoreId")
//            ,waitInStock.holdStoreId.as("holdStoreId")
//            ,waitInStock.goodsOptionId.as("goodsOptionId")
//            ,waitInStock.goodsId.as("goodsId")
        ));
        List<WaitInStockDto> waitInStockDtoList =
                query.from(waitInStock)
                        .leftJoin(provider).on(waitInStock.provId.eq(provider.provId))
                        .leftJoin(store).on(waitInStock.ownStoreId.eq(store.storeId))
                        .leftJoin(store).on(waitInStock.ownStoreId.eq(store.storeId))
                        .fetch();

        return waitInStockDtoList;
    }
}
