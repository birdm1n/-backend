package com.daema.wms.repository;

import com.daema.base.enums.StatusEnum;
import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import com.daema.wms.domain.dto.response.StockDeviceListDto;
import com.daema.wms.domain.dto.response.StockListDto;
import com.daema.wms.repository.custom.CustomStockRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

import static com.daema.commgmt.domain.QOpenStore.openStore;
import static com.daema.commgmt.domain.QStore.store;
import static com.daema.wms.domain.QStock.stock;

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

    private List<StockListDto> searchStockList(StockRequestDto requestDto) {

        StringBuilder sb = new StringBuilder();
        sb.append("select 1 as depth " +
                "           , stock_id " +
                "           , store_id " +
                "           , parent_stock_id " +
                "           , stock_name " +
                "           , stock_type " +
                "           , charger_name " +
                "           , charger_phone " +
                "           , concat(stock_id, '/') as hierarchy" +
                "           , 0 as dvc_cnt " +
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
                "           , 0 as dvc_cnt " +
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
                "           , 0 as dvc_cnt " +
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
                "      order by hierarchy ");


        Query query = em.createNativeQuery(sb.toString(), "StockList")
                .setParameter("regi_store_id", requestDto.getStoreId());

        return query.getResultList();
    }

    private List<StockDeviceListDto> searchStockDeviceList(StockRequestDto requestDto){

        StringBuilder sb = new StringBuilder();

        sb.append("select dvc_id " +
                        "       ,stock_id " +
                        "       ,stock_name " +
                        "       ,hierarchy " +
                        "       ,full_barcode " +
                        "       ,in_stock_amt " +
                        "       ,goods_name " +
                        "       ,model_name " +
                        "       ,go.capacity " +
                        "       ,go.color_name " +
                        "       ,maker " +
                        "       ,telecom " +
                        "       ,cd1.code_nm as telecom_name " +
                        "       ,cd2.code_nm as maker_name " +
                        "  from goods " +
                        "  inner join goods_option go " +
                        "  on goods.goods_id = go.goods_id " +
                        "  inner join ( " +
                        "      select dv.dvc_id " +
                        "           , ss_sd.stock_id " +
                        "           , stock_name " +
                        "           , hierarchy " +
                        "           , full_barcode " +
                        "           , ifnull(inst.in_stock_amt, 0) as in_stock_amt " +
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
                        "                     and parent_stock_id = 0 " +
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
                        "                        and parent.parent_stock_id = 0 " +
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
                        "                             , concat(parent.stock_id, " +
                        "                                      '/', " +
                        "                                      child.stock_id, " +
                        "                                      '/') as hierarchy " +
                        "                          from stock child " +
                        "                          inner join " +
                        "                          stock parent " +
                        "                          on parent.parent_stock_id = 0 " +
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
                        "        left join in_stock as inst " +
                        "        on inst.in_stock_id = ss_sd.stock_type_id " +
                        "            and stock_type = 'IN_STOCK' " +
                        "  ) as stock_dv " +
                        "  on go.goods_option_id = stock_dv.goods_option_id " +
                        "  inner join code_detail cd1 " +
                        "     on goods.telecom = cd1.code_seq " +
                        "  inner join code_detail cd2 " +
                        "     on goods.maker = cd2.code_seq " +
                "          where 1 = 1 ");

        if(requestDto.getStockId() != null) {
            sb.append(" and stock_id = ").append(requestDto.getStockId());
        }
        if(requestDto.getTelecom() != null) {
            sb.append(" and telecom = ").append(requestDto.getTelecom());
        }
        if(requestDto.getMaker() != null) {
            sb.append(" and maker = ").append(requestDto.getMaker());
        }
        if(requestDto.getGoodsId() != null) {
            sb.append(" and goods_id = ").append(requestDto.getGoodsId());
        }
        if(StringUtils.hasText(requestDto.getCapacity())){
            sb.append(" and go.capacity = '").append(requestDto.getCapacity()).append("'");
        }
        if(StringUtils.hasText(requestDto.getColorName())){
            sb.append(" and go.color_name = '").append(requestDto.getCapacity()).append("'");
        }
        if(StringUtils.hasText(requestDto.getFullBarcode())){
            sb.append(" and full_barcode like '%").append(requestDto.getFullBarcode()).append("%'");
        }

        sb.append(" order by hierarchy, goods_name, cd1.code_nm, cd2.code_nm ");

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
                                stock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
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
                                stock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
                                stock.parentStockId.eq(0L),
                                (openStore.telecom.eq(telecom).or(store.telecom.eq(telecom)))
                        )
                        .fetchOne();
        return StockDto;
    }
}
