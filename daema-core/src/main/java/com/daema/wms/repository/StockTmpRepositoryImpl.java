package com.daema.wms.repository;

import com.daema.wms.domain.StockTmp;
import com.daema.wms.repository.custom.CustomStockTmpRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.daema.wms.domain.QDevice.device;
import static com.daema.wms.domain.QStockTmp.stockTmp;

public class StockTmpRepositoryImpl extends QuerydslRepositorySupport implements CustomStockTmpRepository {

    public StockTmpRepositoryImpl() {
        super(StockTmp.class);
    }

    @Override
    public List<StockTmp> getTelkitStockList(long moveType) {

        JPQLQuery<StockTmp> query = getQuerydsl().createQuery();
/*
        WmsEnum.DeliveryType deliveryType;
        Long nextStockId;
        String barcode;
        String selDvcId;
        Long courier;
        String invoiceNo;
        String deliveryMemo;
        */

        query.select(Projections.fields(
                StockTmp.class
                , stockTmp.nowStockId.as("nowStockId")
                , stockTmp.barcode.as("barcode")
                , stockTmp.memo.as("memo")
                , device.dvcId.as("selDvcId")
                )
        )
                .from(stockTmp)
                .innerJoin(device)
                .on(
                        stockTmp.goodsOptionId.eq(device.goodsOption.goodsOptionId)
                        ,stockTmp.moveType.eq(moveType)
                        ,stockTmp.goodsId.isNotNull()
                );

        return query.fetch();
    }
}




