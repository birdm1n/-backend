package com.daema.core.wms.repository;

import com.daema.core.wms.domain.StockTmp;
import com.daema.core.wms.repository.custom.CustomStockTmpRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.daema.core.wms.domain.QDevice.device;
import static com.daema.core.wms.domain.QStockTmp.stockTmp;

public class StockTmpRepositoryImpl extends QuerydslRepositorySupport implements CustomStockTmpRepository {

    public StockTmpRepositoryImpl() {
        super(StockTmp.class);
    }

    @Override
    public List<StockTmp> getTelkitStockList(Long[] moveType) {

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
                        stockTmp.barcode.eq(device.serialNo)
                        ,stockTmp.moveType.in(moveType)
                        ,stockTmp.goodsId.isNotNull()
                );

        return query.fetch();
    }
}




