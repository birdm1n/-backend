package com.daema.wms.repository;

import com.daema.base.util.CommonUtil;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.dto.response.DeviceHistoryResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomDeviceRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

import static com.daema.wms.domain.QDevice.device;
import static com.daema.wms.domain.QInStock.inStock;
import static com.daema.wms.domain.QMoveStock.moveStock;
import static com.daema.wms.domain.QOutStock.outStock;
import static com.daema.wms.domain.QReturnStock.returnStock;

public class DeviceRepositoryImpl extends QuerydslRepositorySupport implements CustomDeviceRepository {

    public DeviceRepositoryImpl() {
        super(Device.class);
    }

    @Override
    public List<DeviceHistoryResponseDto> getDeviceHistory(Long dvcId, Long storeId) {
        JPQLQuery<DeviceHistoryResponseDto> query = getQuerydsl().createQuery();

        DateTimePath<LocalDateTime> aliasRegiDatetime = Expressions.dateTimePath(LocalDateTime.class, "regiDateTime");


        query.select(Projections.fields(
                DeviceHistoryResponseDto.class
                , new CaseBuilder()
                        .when(inStock.inStockId.isNotNull())
                        .then(WmsEnum.StockType.IN_STOCK.getStatusMsg())
                        .when(moveStock.moveStockType.eq(WmsEnum.MoveStockType.STOCK_MOVE))
                        .then(WmsEnum.StockType.STOCK_MOVE.getStatusMsg())
                        .when(moveStock.moveStockType.eq(WmsEnum.MoveStockType.SELL_MOVE))
                        .then(WmsEnum.StockType.SELL_MOVE.getStatusMsg())
                        .when(outStock.outStockType.eq(WmsEnum.OutStockType.STOCK_TRNS))
                        .then(WmsEnum.StockType.STOCK_TRNS.getStatusMsg())
                        .when(outStock.outStockType.eq(WmsEnum.OutStockType.FAULTY_TRNS))
                        .then(WmsEnum.StockType.FAULTY_TRNS.getStatusMsg())
                        .when(outStock.outStockType.eq(WmsEnum.OutStockType.SELL_TRNS))
                        .then(WmsEnum.StockType.SELL_TRNS.getStatusMsg())
                        .when(returnStock.returnStockId.isNotNull())
                        .then(WmsEnum.StockType.OUT_STOCK.getStatusMsg())
                        .otherwise(WmsEnum.StockType.UNKNOWN.getStatusMsg())
                        .as("stockTypeMsg")
                , new CaseBuilder()
                        .when(inStock.regiDateTime.isNotNull())
                        .then(inStock.regiDateTime)
                        .when(moveStock.regiDateTime.isNotNull())
                        .then(moveStock.regiDateTime)
                        .when(outStock.regiDateTime.isNotNull())
                        .then(outStock.regiDateTime)
                        .when(returnStock.regiDateTime.isNotNull())
                        .then(returnStock.regiDateTime)
                        .otherwise(Expressions.nullExpression())
                        .as(aliasRegiDatetime)
                , new CaseBuilder()
                        .when(inStock.store.storeId.isNotNull())
                        .then(inStock.store.storeName)
                        .when(moveStock.store.storeId.isNotNull())
                        .then(moveStock.store.storeName)
                        .when(outStock.store.storeId.isNotNull())
                        .then(outStock.store.storeName)
                        .when(returnStock.store.storeId.isNotNull())
                        .then(returnStock.store.storeName)
                        .otherwise(Expressions.nullExpression())
                        .as("storeName")
                , new CaseBuilder()
                        .when(inStock.regiUserId.isNotNull())
                        .then(inStock.regiUserId.name)
                        .when(moveStock.regiUserId.isNotNull())
                        .then(moveStock.regiUserId.name)
                        .when(outStock.regiUserId.isNotNull())
                        .then(outStock.regiUserId.name)
                        .when(returnStock.regiUserId.isNotNull())
                        .then(returnStock.regiUserId.name)
                        .otherwise(Expressions.nullExpression())
                        .as("regiUserName")
                )
        )
                .from(device)
                .leftJoin(device.inStocks, inStock)
                .leftJoin(device.moveStockList, moveStock)
                .leftJoin(device.outStocks, outStock)
                .leftJoin(device.returnStockList, returnStock)
                .orderBy(aliasRegiDatetime.desc());

        List<DeviceHistoryResponseDto> resultList = query.fetch();

        for (DeviceHistoryResponseDto dto : resultList) {
            dto.setDiffStockRegiDate(CommonUtil.diffLocalDateTimeToDays(dto.getRegiDateTime()));
        }

        return resultList;
    }
}
























