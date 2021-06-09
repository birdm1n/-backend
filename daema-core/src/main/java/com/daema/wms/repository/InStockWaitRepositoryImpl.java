package com.daema.wms.repository;

import com.daema.base.enums.StatusEnum;
import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.dto.response.InStockWaitGroupDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomInStockWaitRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public List<InStockWait> getList(long memberId, long storeId, WmsEnum.InStockStatus inStockStatus) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.selectFrom(inStockWait)
                .where(
                        inStockWait.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
                        inStockWait.inStockStatus.eq(inStockStatus),
                        inStockWait.ownStoreId.eq(storeId).or(inStockWait.holdStoreId.eq(storeId)),
                        inStockWait.regiUserId.seq.eq(memberId)
                )
                .orderBy(inStockWait.regiDateTime.desc())
                .fetch();
    }

    @Override
    public List<InStockWaitGroupDto> groupInStockWaitList(long memberId, long storeId, WmsEnum.InStockStatus inStockStatus) {
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
                        inStockWait.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
                        inStockWait.inStockStatus.eq(inStockStatus),
                        inStockWait.ownStoreId.eq(storeId).or(inStockWait.holdStoreId.eq(storeId)),
                        inStockWait.regiUserId.seq.eq(memberId)
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

    @Override
    public long inStockWaitDuplCk(long storeId, String barcode) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        long resultCount = queryFactory
                .selectFrom(inStockWait)
                .where(
                        inStockWait.ownStoreId.eq(storeId),
                        inStockWait.delYn.eq("N"),
                        inStockWait.rawBarcode.eq(barcode).or(
                                inStockWait.fullBarcode.eq(barcode).or(
                                        inStockWait.serialNo.eq(barcode)
                                )
                        )
                )
                .fetchCount();
        return resultCount;
    }
}
