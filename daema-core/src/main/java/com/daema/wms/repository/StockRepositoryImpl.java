package com.daema.wms.repository;

import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.wms.domain.QStock;
import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import com.daema.wms.domain.dto.response.StockDeviceListDto;
import com.daema.wms.repository.custom.CustomStockRepository;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

import static com.daema.commgmt.domain.QStore.store;
import static com.daema.wms.domain.QStock.stock;
import static com.daema.wms.domain.QStoreStock.storeStock;

public class StockRepositoryImpl extends QuerydslRepositorySupport implements CustomStockRepository {

    public StockRepositoryImpl() {
        super(Stock.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public HashMap<String, List> getStockAndDeviceList(StockRequestDto requestDto) {

        HashMap<String, List> retMap = new HashMap<>();

        retMap.put("stockList", searchStockList(requestDto));
        retMap.put("stockDeviceList", searchStockDeviceList(requestDto));

        return retMap;
    }

    private List<Stock> searchStockList(StockRequestDto requestDto) {

        QStock parent = new QStock("parent");
        QStock child = new QStock("child");

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.selectFrom(parent)
                .distinct()
                .leftJoin(parent.childStockList, child)
                .fetchJoin()
                .where(parent.parentStock.isNull()
                        , parent.regiStoreId.eq(requestDto.getStoreId())
                        , parent.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
                )
                .orderBy(
                        new CaseBuilder()
                                .when(parent.stockType.eq(TypeEnum.STOCK_TYPE_O.getStatusCode()))
                                .then("A")
                                .when(parent.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                                .then("B")
                                .when(parent.stockType.eq(TypeEnum.STOCK_TYPE_S.getStatusCode()))
                                .then("C")
                                .otherwise(parent.stockType).asc()
                        ,
                        parent.stockName.asc(), child.stockName.asc()
                )
                .fetch();
    }

    private List<StockDeviceListDto> searchStockDeviceList(StockRequestDto requestDto) {

        StringBuilder sb = new StringBuilder();

        sb.append("select dvc_id " +
                "       ,stock_id " +
                "       ,stock_name " +
                "       ,hierarchy " +
                "       ,raw_barcode " +
                "       ,in_stock_amt " +
                "       ,goods_name " +
                "       ,model_name " +
                "       ,go.capacity " +
                "       ,go.color_name " +
                "       ,maker_code_id " +
                "       ,telecom_code_id " +
                "       ,cd1.code_name as telecom_name " +
                "       ,cd2.code_name as maker_name " +
                "  from goods as goods " +
                "  inner join goods_option go " +
                "  on goods.goods_id = go.goods_id " +
                "  inner join ( " +
                "      select dv.dvc_id " +
                "           , ss_sd.stock_id " +
                "           , stock_name " +
                "           , hierarchy " +
                "           , raw_barcode " +
                "           , full_barcode " +
                "           , serial_no " +
                "           , ifnull(in_stock_amt, 0) as in_stock_amt " +
                "           , goods_option_id " +
                "        from device as dv " +
                "        inner join ( " +
                "            select dvc_id " +
                "                   ,stock_type " +
                "                   ,stock_type_id " +
                "                   ,stock_id " +
                "                   ,stock_name " +
                "                   ,hierarchy " +
                "              from store_stock as ss " +
                "              inner join ( " +
                "                  select stock_id " +
                "                       , stock_name " +
                "                       , concat(stock_id, " +
                "                                '/') as hierarchy " +
                "                    from stock " +
                "                   where regi_store_id = :regi_store_id " +
                "                     and parent_stock_id is null " +
                "                     and del_yn = 'N' " +
                "                   union " +
                "                  select child.stock_id " +
                "                       , child.stock_name " +
                "                       , concat(parent.stock_id, " +
                "                                '/', " +
                "                                child.stock_id, " +
                "                                '/') as hierarchy " +
                "                    from stock child " +
                "                    inner join " +
                "                    stock parent " +
                "                    on child.regi_store_id = :regi_store_id " +
                "                        and child.del_yn = 'N' " +
                "                        and parent.del_yn = 'N' " +
                "                        and parent.parent_stock_id is null " +
                "                        and child.parent_stock_id = parent.stock_id " +
                "                   union " +
                "                  select child.stock_id " +
                "                       , child.stock_name " +
                "                       , concat(parent.hierarchy, " +
                "                                child.stock_id, " +
                "                                '/') as hierarchy " +
                "                    from stock child " +
                "                    inner join " +
                "                    ( " +
                "                        select child.stock_id " +
                "                             , child.store_id " +
                "                             , child.parent_stock_id " +
                "                             , child.stock_name " +
                "                             , child.stock_type " +
                "                             , child.charger_name " +
                "                             , child.charger_phone " +
                "                             , child.charger_phone1 " +
                "                             , child.charger_phone2 " +
                "                             , child.charger_phone3 " +
                "                             , concat(parent.stock_id, " +
                "                                      '/', " +
                "                                      child.stock_id, " +
                "                                      '/') as hierarchy " +
                "                          from stock child " +
                "                          inner join " +
                "                          stock parent " +
                "                          on parent.parent_stock_id is null " +
                "                              and child.parent_stock_id = parent.stock_id " +
                "                         where child.regi_store_id = :regi_store_id " +
                "                           and child.del_yn = 'N' " +
                "                           and parent.del_yn = 'N' " +
                "                    ) as parent " +
                "                    on parent.stock_id = child.parent_stock_id " +
                "                        and child.regi_store_id = :regi_store_id " +
                "                        and child.del_yn = 'N' " +
                "              ) as sd " +
                "              on ss.store_id = :regi_store_id " +
                "                  and stock_yn = 'Y' " +
                "                  and ss.next_stock_id = sd.stock_id " +
                "        ) as ss_sd " +
                "        on dv.dvc_id = ss_sd.dvc_id " +
                "  ) as stock_dv " +
                "  on go.goods_option_id = stock_dv.goods_option_id " +
                "  inner join code_detail cd1 " +
                "     on goods.telecom_code_id = cd1.code_id " +
                "  inner join code_detail cd2 " +
                "     on goods.maker_code_id = cd2.code_id " +
                "          where 1 = 1 ");

        if (requestDto.getStockId() != null) {
            sb.append(" and stock_id = ").append(requestDto.getStockId());
        }
        if (requestDto.getTelecom() != null) {
            sb.append(" and telecom_code_id = ").append(requestDto.getTelecom());
        }
        if (requestDto.getMaker() != null) {
            sb.append(" and maker_code_id = ").append(requestDto.getMaker());
        }
        if (requestDto.getGoodsId() != null) {
            sb.append(" and goods.goods_id = ").append(requestDto.getGoodsId());
        }
        if (StringUtils.hasText(requestDto.getCapacity())) {
            sb.append(" and go.capacity = '").append(requestDto.getCapacity()).append("'");
        }
        if (StringUtils.hasText(requestDto.getColorName())) {
            sb.append(" and go.color_name = '").append(requestDto.getColorName()).append("'");
        }
        if (StringUtils.hasText(requestDto.getBarcode())) {
            sb.append(" and (")
                    .append("raw_barcode like '%").append(requestDto.getBarcode()).append("%'")
                    .append("or full_barcode like '%").append(requestDto.getBarcode()).append("%'")
                    .append("or serial_no like '%").append(requestDto.getBarcode()).append("%'")
                    .append(") ");
        }

        sb.append(" order by hierarchy, goods_name, cd1.code_name, cd2.code_name ");

        Query query = em.createNativeQuery(sb.toString(), "StockDeviceList")
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
        ));
        return query.from(stock)
                .innerJoin(store).on(
                        stock.storeId.eq(store.storeId)
                )

                .where(
                        stock.regiStoreId.eq(storeId),
                        stock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
                        stock.parentStock.isNull(),
                        store.telecom.eq(telecom)
                )
                .orderBy(
                        new CaseBuilder()
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_O.getStatusCode()))
                                .then("A")
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                                .then("B")
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_S.getStatusCode()))
                                .then("C")
                                .otherwise(stock.stockType).asc()
                        ,
                        stock.stockName.asc()
                )
                .fetch();
    }

    @Override
    public SelectStockDto getStock(Long storeId, Integer telecom, Long stockId) {
        JPQLQuery<SelectStockDto> query = getQuerydsl().createQuery();
        query.select(Projections.fields(
                SelectStockDto.class
                , stock.stockId.as("stockId")
                , stock.stockName.as("stockName")
                , store.storeId.as("storeId")
        ));

        return query.from(stock)
                .innerJoin(store)
                .on(
                        stock.storeId.eq(store.storeId)
                )
                .where(
                        stock.stockId.eq(stockId),
                        stock.regiStoreId.eq(storeId),
                        stock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
                        stock.parentStock.isNull(),
                        store.telecom.eq(telecom)
                )
                .orderBy(
                        new CaseBuilder()
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_O.getStatusCode()))
                                .then("A")
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                                .then("B")
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_S.getStatusCode()))
                                .then("C")
                                .otherwise(stock.stockType).asc()
                        ,
                        stock.stockName.asc()
                )
                .fetchOne();
    }


    @Override
    public List<SelectStockDto> innerStockList(long storeId) {
        JPQLQuery<SelectStockDto> query = getQuerydsl().createQuery();
        query.select(Projections.fields(
                SelectStockDto.class
                , stock.stockId.as("stockId")
                , stock.stockName.as("stockName")
        ));

        return query.from(stock)
                .where(
                        stock.regiStoreId.eq(storeId),
                        stock.storeId.ne(storeId),
                        stock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
                        stock.parentStock.isNull()
                )
                .orderBy(
                        new CaseBuilder()
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_O.getStatusCode()))
                                .then("A")
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                                .then("B")
                                .when(stock.stockType.eq(TypeEnum.STOCK_TYPE_S.getStatusCode()))
                                .then("C")
                                .otherwise(stock.stockType).asc()
                        ,
                        stock.stockName.asc()
                )
                .fetch();
    }

    @Override
    public Long stockHasDevice(Long stockId, Long regiStoreId) {
        JPQLQuery<Stock> query = getQuerydsl().createQuery();

        query.select(stock);

        query.from(stock)
                .leftJoin(storeStock)
                .on(
                        stock.stockId.eq(stockId)
                                .and(stock.regiStoreId.eq(regiStoreId))
                                .and(stock.stockId.eq(storeStock.prevStock.stockId)
                                        .or(Expressions.predicate(Ops.WRAPPED
                                                , stock.stockId.eq(storeStock.nextStock.stockId))
                                        )
                                )
                )
                .where(storeStock.stockYn.eq(StatusEnum.FLAG_Y.getStatusMsg()));

        return query.fetchCount();
    }
}























