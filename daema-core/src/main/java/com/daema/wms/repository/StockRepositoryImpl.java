package com.daema.wms.repository;

import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import com.daema.wms.domain.dto.response.StockListDto;
import com.daema.wms.repository.custom.CustomStockRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.daema.commgmt.domain.QOpenStore.openStore;
import static com.daema.wms.domain.QStock.stock;
import static com.daema.commgmt.domain.QStore.store;

public class StockRepositoryImpl extends QuerydslRepositorySupport implements CustomStockRepository {

    public StockRepositoryImpl() {
        super(Stock.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<StockListDto> getStockList(StockRequestDto requestDto) {

        StringBuilder sb = new StringBuilder();
        sb.append("select 1 as depth " +
                "           , stock_id " +
                "           , store_id " +
                "           , parent_stock_id " +
                "           , stock_name " +
                "           , stock_type " +
                "           , charger_name " +
                "           , charger_phone " +
                "           , concat(stock_id, '/') as hierarchy " +
                "        from stock " +
                "       where regi_store_id = :regi_store_id " +
                "         and parent_stock_id = 0 " +
                "         and del_yn = 'N' " +
                " " +
                "       union " +
                " " +
                "      select 2 as depth " +
                "           , child.stock_id " +
                "           , child.store_id " +
                "           , child.parent_stock_id " +
                "           , child.stock_name " +
                "           , child.stock_type " +
                "           , child.charger_name " +
                "           , child.charger_phone " +
                "           , concat(parent.stock_id, '/', child.stock_id, '/') as hierarchy " +
                "        from stock child " +
                "        inner join stock parent " +
                "        on child.regi_store_id = :regi_store_id " +
                "            and child.del_yn = 'N' " +
                "            and parent.del_yn = 'N' " +
                "            and parent.parent_stock_id = 0 " +
                "            and child.parent_stock_id = parent.stock_id " +
                " " +
                "       union " +
                " " +
                "      select 3 as depth " +
                "           , child.stock_id " +
                "           , child.store_id " +
                "           , child.parent_stock_id " +
                "           , child.stock_name " +
                "           , child.stock_type " +
                "           , child.charger_name " +
                "           , child.charger_phone " +
                "           , concat(parent.hierarchy, child.stock_id, '/') as hierarchy " +
                "        from stock child " +
                "        inner join (select child.stock_id " +
                "                         , child.store_id " +
                "                         , child.parent_stock_id " +
                "                         , child.stock_name " +
                "                         , child.stock_type " +
                "                         , child.charger_name " +
                "                         , child.charger_phone " +
                "                         , concat(parent.stock_id, '/', child.stock_id, '/') as hierarchy " +
                "                      from stock child " +
                "                      inner join stock parent " +
                "                      on parent.parent_stock_id = 0 " +
                "                          and child.parent_stock_id = parent.stock_id " +
                "                     where child.regi_store_id = :regi_store_id " +
                "                       and child.del_yn = 'N' " +
                "                       and parent.del_yn = 'N') as parent " +
                "        on parent.stock_id = child.parent_stock_id " +
                "            and child.regi_store_id = :regi_store_id " +
                "            and child.del_yn = 'N' " +
                "      order by hierarchy + '' ");


        Query query = em.createNativeQuery(sb.toString(), "StockList")
                .setParameter("regi_store_id", requestDto.getStoreId());

        return query.getResultList();
    }

    @Override
    public List<SelectStockDto> selectStockList(long storeId, Integer telecom) {
        JPQLQuery<SelectStockDto> query = getQuerydsl().createQuery();
        query.select(Projections.fields(
                SelectStockDto.class
                , stock.stockId.as("stockId")
                , stock.stockName.as("stockName")
                , stock.stockType.as("stockType")
                , new CaseBuilder()
                        .when(stock.stockType.eq("O"))
                        .then(openStore.storeId)
                        .otherwise(store.storeId).as("storeId")
        ));

        List<SelectStockDto> selectStockListDto =
                query.from(stock)
                        .leftJoin(openStore)
                        .on(
                                stock.storeId.eq(openStore.openStoreId),
                                stock.stockType.eq("O")
                        )
                        .leftJoin(store)
                        .on(
                                stock.storeId.eq(store.storeId),
                                (stock.stockType.eq("I").or(stock.stockType.eq("S")))
                        )
                        .where(
                                stock.regiStoreId.eq(storeId),
                                stock.delYn.eq("N"),
                                stock.parentStockId.eq(0L),
                                (openStore.telecom.eq(telecom).or(store.telecom.eq(telecom)))
                        )
                        .fetch();
        return selectStockListDto;
    }

    @Override
    public SelectStockDto getStock(Long storeId, Integer telecom, Long stockId) {
        JPQLQuery<SelectStockDto> query = getQuerydsl().createQuery();
        query.select(Projections.fields(
                SelectStockDto.class
                , stock.stockId.as("stockId")
                , stock.stockName.as("stockName")
                , stock.stockType.as("stockType")
                , new CaseBuilder()
                        .when(stock.stockType.eq("O"))
                        .then(openStore.storeId)
                        .otherwise(store.storeId).as("storeId")
        ));

        SelectStockDto StockDto =
                query.from(stock)
                        .leftJoin(openStore)
                        .on(
                                stock.storeId.eq(openStore.openStoreId),
                                stock.stockType.eq("O")
                        )
                        .leftJoin(store)
                        .on(
                                stock.storeId.eq(store.storeId),
                                (stock.stockType.eq("I").or(stock.stockType.eq("S")))
                        )
                        .where(
                                stock.stockId.eq(stockId),
                                stock.regiStoreId.eq(storeId),
                                stock.delYn.eq("N"),
                                stock.parentStockId.eq(0L),
                                (openStore.telecom.eq(telecom).or(store.telecom.eq(telecom)))
                        )
                        .fetchOne();
        return StockDto;
    }
}
