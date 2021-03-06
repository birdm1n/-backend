package com.daema.core.wms.repository;

import com.daema.core.base.enums.StatusEnum;
import com.daema.core.wms.domain.InStockWait;
import com.daema.core.wms.dto.response.InStockWaitGroupDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.repository.custom.CustomInStockWaitRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.daema.core.wms.domain.QInStockWait.inStockWait;

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
    public long inStockWaitDuplCk(long storeId, String barcode, Long goodsId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        long resultCount = queryFactory
                .selectFrom(inStockWait)
                .where(
                        inStockWait.ownStoreId.eq(storeId),
                        inStockWait.delYn.eq("N"),
                        inStockWait.rawBarcode.eq(barcode)
                        /*  rawBarcode ??? del_yn = 'N' ????????? ?????????.
                            ?????? ??????????????? fullBarcode ??? ??? 1????????? ???????????? ????????? ???????????? ????????? ??? ??????.
                            ?????? ????????? ?????? ?????? rawBarcode ????????? ??????. 20210622
                        inStockWait.rawBarcode.eq(barcode).or(
                                inStockWait.fullBarcode.eq(barcode).or(
                                        inStockWait.serialNo.eq(barcode)
                                )
                        ),
                        eqGoodsId(goodsId)
                        */

                )
                .fetchCount();
        return resultCount;
    }

    private BooleanExpression eqGoodsId(Long goodsId) {
        if (goodsId == null) {
            return null;
        }
        return inStockWait.goodsId.eq(goodsId);
    }
}
