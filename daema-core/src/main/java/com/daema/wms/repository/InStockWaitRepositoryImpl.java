package com.daema.wms.repository;

import com.daema.wms.domain.InStock;
import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.dto.response.InStockWaitGroupDto;
import com.daema.wms.repository.custom.CustomInStockWaitRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.daema.wms.domain.QInStockWait.inStockWait;

public class InStockWaitRepositoryImpl extends QuerydslRepositorySupport implements CustomInStockWaitRepository {

    public InStockWaitRepositoryImpl() {
        super(InStockWait.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<InStockWaitGroupDto> groupInStockWaitList(long storeId, InStock.StockStatus inStockStatus) {
        JPQLQuery<InStockWaitGroupDto> query = getQuerydsl().createQuery();


        return query.select(Projections.fields(
                InStockWaitGroupDto.class
                , inStockWait.telecomName.as("telecomName")
                , inStockWait.stockName.as("stockName")
                , inStockWait.makerName.as("makerName")
                , inStockWait.goodsName.as("goodsName")
                , inStockWait.modelName.as("modelName")
                , inStockWait.capacity.as("capacity")
                , inStockWait.colorName.as("colorName")
                , inStockWait.goodsName.count().as("totalCount")
        ))
                .from(inStockWait)
                .where(
                        inStockWait.delYn.eq("N"),
                        inStockWait.inStockStatus.eq(inStockStatus),
                        inStockWait.ownStoreId.eq(storeId).or(inStockWait.holdStoreId.eq(storeId))
                )
                .groupBy(
                        inStockWait.telecomName
                        , inStockWait.stockName
                        , inStockWait.makerName
                        , inStockWait.goodsName
                        , inStockWait.modelName
                        , inStockWait.capacity
                        , inStockWait.colorName
                )
                .orderBy(inStockWait.regiDateTime.desc())
                .fetch();
    }
}
