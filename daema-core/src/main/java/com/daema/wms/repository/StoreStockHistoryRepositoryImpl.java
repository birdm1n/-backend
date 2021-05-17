package com.daema.wms.repository;

import com.daema.wms.domain.QStoreStock;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.StoreStockHistory;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomStoreStockHistoryRepository;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.daema.wms.domain.QStoreStockHistory.storeStockHistory;

public class StoreStockHistoryRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreStockHistoryRepository {

    public StoreStockHistoryRepositoryImpl() {
        super(StoreStockHistory.class);
    }

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * arrange : store_stock_id 중, 삭제가 아닌 건과 현재 type 과 typeId 를 제외하고 모두 WAIT 업데이트
     *
     * delete : arrange & store_stock_id 중, USE 를 가져와서 store_stock 반영
     * @Required StoreStock.stockType
     * @Required StoreStock.stockTypeId
     * @Required StoreStock.store
     * @Required StoreStock.device
     *
     * @param storeStock
     * @param delFlag
     */
    @Override
    public void arrangeStoreStockHistory(StoreStock storeStock, boolean delFlag) {

        JPAUpdateClause updateHistory = new JPAUpdateClause(em, storeStockHistory);

        if (!delFlag) {

            updateHistory
                    .set(storeStockHistory.historyStatus, WmsEnum.HistoryStatus.WAIT)
                    .where(
                            storeStockHistory.storeStock.eq(storeStock)
                                    .and(storeStockHistory.historyStatus.ne(WmsEnum.HistoryStatus.DEL))
                                    .and(
                                            Expressions.predicate(Ops.WRAPPED,
                                                    storeStockHistory.stockTypeId.eq(storeStock.getStockTypeId())
                                                            .and(storeStockHistory.stockType.eq(storeStock.getStockType()))
                                            ).not()
                                    )
                    )
                    .execute();

        } else {

            //type, typeId 일치하는 건, del update 처리
            updateHistory
                    .set(storeStockHistory.historyStatus, WmsEnum.HistoryStatus.DEL)
                    .where(storeStockHistory.stockType.eq(storeStock.getStockType())
                            .and(storeStockHistory.stockTypeId.eq(storeStock.getStockTypeId()))
                            .and(storeStockHistory.store.eq(storeStock.getStore()))
                            .and(storeStockHistory.device.eq(storeStock.getDevice()))
                    )
                    .execute();

            //where is not del status : maxId  -> use update 처리
            updateHistory
                    .set(storeStockHistory.historyStatus, WmsEnum.HistoryStatus.USE)
                    .where(storeStockHistory.storeStockHistoryId.eq(
                            JPAExpressions
                                    .select(storeStockHistory.storeStockHistoryId.max())
                                    .from(storeStockHistory)
                                    .where(
                                            storeStockHistory.store.eq(storeStock.getStore())
                                            .and(storeStockHistory.device.eq(storeStock.getDevice()))
                                            .and(storeStockHistory.historyStatus.ne(WmsEnum.HistoryStatus.DEL))
                                    )
                            )
                    )
                    .execute();


            JPQLQuery<StoreStockHistory> query = getQuerydsl().createQuery();

            query.from(storeStockHistory)
                    .where(
                            storeStockHistory.storeStock.eq(storeStock)
                                    .and(storeStockHistory.historyStatus.eq(WmsEnum.HistoryStatus.USE))
                    );

            StoreStockHistory storeStockHistoryInfo = query.fetchFirst();

            if (storeStockHistoryInfo != null) {
                QStoreStock qStoreStock = QStoreStock.storeStock;

                JPAUpdateClause updateStoreStock = new JPAUpdateClause(em, qStoreStock);

                updateStoreStock
                        .set(qStoreStock.stockType, storeStockHistoryInfo.getStockType())
                        .set(qStoreStock.stockTypeId, storeStockHistoryInfo.getStockTypeId())
                        .set(qStoreStock.updUserId, storeStockHistoryInfo.getUpdUserId())
                        .set(qStoreStock.updDateTime, storeStockHistoryInfo.getUpdDateTime())
                        .set(qStoreStock.prevStock, storeStockHistoryInfo.getPrevStock())
                        .set(qStoreStock.nextStock, storeStockHistoryInfo.getNextStock())
                        .where(qStoreStock.storeStockId.eq(storeStockHistoryInfo.getStoreStock().getStoreStockId()))
                        .execute();

            } else {
                throw new EntityExistsException("IS_NOT_PRESENT");
            }
        }
    }
}




















