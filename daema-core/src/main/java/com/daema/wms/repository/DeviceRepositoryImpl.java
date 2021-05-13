package com.daema.wms.repository;

import com.daema.base.enums.StatusEnum;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.dto.response.DeviceHistoryResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomDeviceRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.daema.base.domain.QMember.member;
import static com.daema.commgmt.domain.QStore.store;
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
        JPQLQuery<DeviceHistoryResponseDto> inStockQuery = getQuerydsl().createQuery();
        JPQLQuery<DeviceHistoryResponseDto> moveStockQuery = getQuerydsl().createQuery();
        JPQLQuery<DeviceHistoryResponseDto> outStockQuery = getQuerydsl().createQuery();
        JPQLQuery<DeviceHistoryResponseDto> returnStockQuery = getQuerydsl().createQuery();

        inStockQuery.select(Projections.fields(
                DeviceHistoryResponseDto.class
                , Expressions.asString(WmsEnum.StockType.IN_STOCK.getStatusMsg()).as("stockTypeMsg")
                , inStock.regiDateTime.as("regiDateTime")
                , inStock.store.storeName.as("storeName")
                , inStock.regiUserId.name.as("regiUserName")
                )
        )
                .from(device)
                .innerJoin(device.inStocks, inStock)
                .on(device.dvcId.eq(dvcId).and(device.store.storeId.eq(storeId)))
                .innerJoin(inStock.store, store)
                .on(inStock.device.dvcId.eq(dvcId).and(inStock.store.storeId.eq(storeId)))
                .innerJoin(inStock.regiUserId, member)
                .on(inStock.device.dvcId.eq(dvcId).and(inStock.regiUserId.seq.eq(member.seq)));

        List<DeviceHistoryResponseDto> historyList = new ArrayList<>(inStockQuery.fetch());

        moveStockQuery.select(Projections.fields(
                DeviceHistoryResponseDto.class
                , new CaseBuilder()
                        .when(moveStock.moveStockType.eq(WmsEnum.MoveStockType.STOCK_MOVE))
                        .then(WmsEnum.StockType.STOCK_MOVE.getStatusMsg())
                        .when(moveStock.moveStockType.eq(WmsEnum.MoveStockType.SELL_MOVE))
                        .then(WmsEnum.StockType.SELL_MOVE.getStatusMsg())
                        .otherwise(WmsEnum.StockType.UNKNOWN.getStatusMsg())
                        .as("stockTypeMsg")
                , moveStock.regiDateTime.as("regiDateTime")
                , moveStock.store.storeName.as("storeName")
                , moveStock.regiUserId.name.as("regiUserName")
                )
        )
                .from(device)
                .innerJoin(device.moveStockList, moveStock)
                .on(device.dvcId.eq(dvcId).and(device.store.storeId.eq(storeId)))
                .innerJoin(moveStock.store, store)
                .on(moveStock.device.dvcId.eq(dvcId).and(moveStock.store.storeId.eq(storeId)))
                .innerJoin(moveStock.regiUserId, member)
                .on(moveStock.device.dvcId.eq(dvcId).and(moveStock.regiUserId.seq.eq(member.seq)));

        historyList.addAll(moveStockQuery.fetch());


        outStockQuery.select(Projections.fields(
                DeviceHistoryResponseDto.class
                , new CaseBuilder()
                        .when(outStock.outStockType.eq(WmsEnum.OutStockType.STOCK_TRNS))
                        .then(WmsEnum.StockType.STOCK_TRNS.getStatusMsg())
                        .when(outStock.outStockType.eq(WmsEnum.OutStockType.FAULTY_TRNS))
                        .then(WmsEnum.StockType.FAULTY_TRNS.getStatusMsg())
                        .when(outStock.outStockType.eq(WmsEnum.OutStockType.SELL_TRNS))
                        .then(WmsEnum.StockType.SELL_TRNS.getStatusMsg())
                        .otherwise(WmsEnum.StockType.UNKNOWN.getStatusMsg())
                        .as("stockTypeMsg")
                , outStock.regiDateTime.as("regiDateTime")
                , outStock.store.storeName.as("storeName")
                , outStock.regiUserId.name.as("regiUserName")
                )
        )
                .from(device)
                .innerJoin(device.outStocks, outStock)
                .on(device.dvcId.eq(dvcId).and(device.store.storeId.eq(storeId)))
                .innerJoin(outStock.store, store)
                .on(outStock.device.dvcId.eq(dvcId).and(outStock.store.storeId.eq(storeId)))
                .innerJoin(outStock.regiUserId, member)
                .on(outStock.device.dvcId.eq(dvcId).and(outStock.regiUserId.seq.eq(member.seq)));

        historyList.addAll(outStockQuery.fetch());


        returnStockQuery.select(Projections.fields(
                DeviceHistoryResponseDto.class
                , Expressions.asString(WmsEnum.StockType.RETURN_STOCK.getStatusMsg()).as("stockTypeMsg")
                , returnStock.regiDateTime.as("regiDateTime")
                , returnStock.store.storeName.as("storeName")
                , returnStock.regiUserId.name.as("regiUserName")
                )
        )
                .from(device)
                .innerJoin(device.returnStockList, returnStock)
                .on(device.dvcId.eq(dvcId).and(device.store.storeId.eq(storeId)))
                .innerJoin(returnStock.store, store)
                .on(returnStock.device.dvcId.eq(dvcId).and(returnStock.store.storeId.eq(storeId)))
                .innerJoin(returnStock.regiUserId, member)
                .on(returnStock.device.dvcId.eq(dvcId).and(returnStock.regiUserId.seq.eq(member.seq)))
                .where(returnStock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()));

        historyList.addAll(returnStockQuery.fetch());

        if (historyList != null
                && historyList.size() > 0) {

            Collections.sort(historyList, (dto1, dto2) -> dto2.getRegiDateTime().compareTo(dto1.getRegiDateTime()));
        }

        return historyList;
    }
}
























