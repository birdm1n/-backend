package com.daema.wms.repository;

import com.daema.wms.domain.QStoreStock;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.StoreStockHistory;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomStoreStockHistoryRepository;
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

    //arrange : store_stock_id 중, 삭제가 아닌 건과 현재 typeId 를 제외하고 모두 WAIT 업데이트
    //delete : arrange & store_stock_id 중, USE 를 가져와서 store_stock 반영
    @Override
    public void arrangeStoreStockHistory(StoreStock storeStock, boolean delFlag) {

        JPAUpdateClause updateHistory = new JPAUpdateClause(em, storeStockHistory);

        updateHistory
                .set(storeStockHistory.historyStatus, WmsEnum.HistoryStatus.WAIT)
                .where(
                        storeStockHistory.storeStock.eq(storeStock)
                        .and(storeStockHistory.historyStatus.ne(WmsEnum.HistoryStatus.DEL))
                        .and(storeStockHistory.stockTypeId.ne(storeStock.getStockTypeId()))
                )
                .execute();

        if(delFlag){
            JPQLQuery<StoreStockHistory> query = getQuerydsl().createQuery();

            query.from(storeStockHistory)
                    .where(
                            storeStockHistory.storeStock.eq(storeStock)
                            .and(storeStockHistory.historyStatus.eq(WmsEnum.HistoryStatus.USE))
                    );

            StoreStockHistory storeStockHistoryInfo = query.fetchFirst();

            if(storeStockHistoryInfo != null){
                QStoreStock qStoreStock = QStoreStock.storeStock;

                JPAUpdateClause updateStoreStock = new JPAUpdateClause(em, qStoreStock);

                updateStoreStock
                        .set(qStoreStock.stockType, storeStockHistoryInfo.getStockType())
                        .set(qStoreStock.stockTypeId, storeStockHistoryInfo.getStockTypeId())
                        .set(qStoreStock.prevStock, storeStockHistoryInfo.getPrevStock())
                        .set(qStoreStock.nextStock, storeStockHistoryInfo.getNextStock())
                        .where(qStoreStock.storeStockId.eq(storeStockHistoryInfo.getStoreStock().getStoreStockId()))
                        .execute();

            }else{
                throw new EntityExistsException("IS_NOT_PRESENT");
            }
        }
    }
}