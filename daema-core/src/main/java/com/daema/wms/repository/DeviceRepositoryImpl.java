package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.base.enums.StatusEnum;
import com.daema.commgmt.domain.attr.NetworkAttribute;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.QStock;
import com.daema.wms.domain.dto.request.DeviceCurrentRequestDto;
import com.daema.wms.domain.dto.response.DeviceCurrentResponseDto;
import com.daema.wms.domain.dto.response.DeviceHistoryResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomDeviceRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.daema.base.domain.QMember.member;
import static com.daema.commgmt.domain.QGoods.goods;
import static com.daema.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.commgmt.domain.QStore.store;
import static com.daema.wms.domain.QDelivery.delivery;
import static com.daema.wms.domain.QDevice.device;
import static com.daema.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.wms.domain.QInStock.inStock;
import static com.daema.wms.domain.QMoveStock.moveStock;
import static com.daema.wms.domain.QOutStock.outStock;
import static com.daema.wms.domain.QProvider.provider;
import static com.daema.wms.domain.QReturnStock.returnStock;
import static com.daema.wms.domain.QStock.stock;
import static com.daema.wms.domain.QStoreStock.storeStock;

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

    @Override
    public Page<DeviceCurrentResponseDto> getDeviceCurrentPage(DeviceCurrentRequestDto requestDto) {
        JPQLQuery<DeviceCurrentResponseDto> query = getQuerydsl().createQuery();

        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail maker = new QCodeDetail("maker");
        QStock prevStock = new QStock("prevStock");
        QStock nextStock = new QStock("nextStock");

        query.select(Projections.fields(
                DeviceCurrentResponseDto.class
                , device.dvcId.as("dvcId")
                , inStock.regiDateTime.as("inStockRegiDate")
                , moveStock.regiDateTime.as("moveStockRegiDate")
                , provider.provId.as("provId")
                , prevStock.stockId.as("prevStockId")
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockId.as("nextStockId")
                , nextStock.stockName.as("nextStockName")
                , inStock.statusStr.as("statusStr")
                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")
                , goodsOption.capacity.as("capacity")
                , goodsOption.colorName.as("colorName")
                , device.fullBarcode.as("fullBarcode")
                , device.inStockAmt.as("inStockAmt")
                , deviceStatus.inStockStatus.as("inStockStatus")
                , deviceStatus.productFaultyYn.as("productFaultyYn")
                , deviceStatus.extrrStatus.as("extrrStatus")
                , deviceStatus.productMissYn.as("productMissYn")
                , deviceStatus.missProduct.as("missProduct")
                , deviceStatus.ddctAmt.as("ddctAmt")
                , deviceStatus.addDdctAmt.as("addDdctAmt")
                , returnStock.returnStockAmt.as("returnStockAmt")
                , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                , maker.codeNm.as("makerName")
                , telecom.codeNm.as("telecomName")
                , delivery.deliveryType.as("deliveryType")
                , delivery.deliveryStatus.as("deliveryStatus")


        ))
                .from(device)
                .innerJoin(device.store, store).on
                (
                        store.storeId.eq(requestDto.getStoreId())
                )
                .innerJoin(device.inStocks, inStock).on
                (
                        inStock.delYn.eq("N")
                )
                .innerJoin(device.deviceStatusList, deviceStatus).on
                (
                        deviceStatus.delYn.eq("N")
                )
                .innerJoin(inStock.provider, provider)
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(device.storeStock, storeStock)
                .innerJoin(storeStock.prevStock, prevStock)
                .innerJoin(storeStock.nextStock, nextStock)
                .leftJoin(device.moveStockList, moveStock).on
                (
                        moveStock.delYn.eq("N")
                )
                .innerJoin(moveStock.delivery, delivery)
                .leftJoin(deviceStatus.returnStock, returnStock)
                .innerJoin(maker).on
                (
                        goods.maker.eq(maker.codeSeq)
                )
                .innerJoin(telecom).on
                (
                        goods.networkAttribute.telecom.eq(telecom.codeSeq)
                )
                .where(
                        eqMaker(requestDto.getMaker()),
                        eqTelecom(requestDto.getTelecom()),
                        eqProvId(requestDto.getProvId()),
                        eqNextStockId(requestDto.getNextStockId()),
                        eqInStockStatus(requestDto.getInStockStatus()),
                        eqStatusStr(requestDto.getStatusStr()),
                        eqMaker(requestDto.getMaker()),
                        eqGoodsId(requestDto.getGoodsId()),
                        eqModelName(requestDto.getModelName()),
                        eqCapacity(requestDto.getCapacity()),
                        eqColorName(requestDto.getColorName()),
                        containsFullBarcode(requestDto.getFullBarcode()),
                        eqInStockStatus(requestDto.getInStockStatus()),
                        eqFaultyYn(requestDto.getProductFaultyYn()),
                        eqExtrrStatus(requestDto.getExtrrStatus()),
                        eqDeliveryType(requestDto.getDeliveryType()),
                        eqDeliveryStatus(requestDto.getDeliveryStatus()),
                        betweenMoveStockRegDt(requestDto.getMoveStockRegiDate(), requestDto.getMoveStockRegiDate()),
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate())
                )
                .orderBy(device.regiDateTime.desc());


        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);


        QueryResults<DeviceCurrentResponseDto> resultList = query.fetchResults();
        return new PageImpl<>(resultList.getResults(), pageable, resultList.getTotal());
    }


    private BooleanExpression betweenInStockRegDt(String startDt, String endDt) {
        if (StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)) {
            return null;
        }
        return inStock.regiDateTime.between(
                RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
        );
    }

    private BooleanExpression betweenMoveStockRegDt(String startDt, String endDt) {
        if (StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)) {
            return null;
        }
        return moveStock.regiDateTime.between(
                RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
        );
    }

    private BooleanExpression eqModelName(String modelName) {
        if (StringUtils.isEmpty(modelName)) {
            return null;
        }
        return goods.modelName.eq(modelName);
    }

    private BooleanExpression eqTelecom(Integer name) {
        if (name == null) {
            return null;
        }
        return goods.networkAttribute.telecom.in(name);
    }

    private BooleanExpression eqMaker(Integer maker) {
        if (maker == null) {
            return null;
        }
        return goods.maker.eq(maker);
    }

    private BooleanExpression eqProvId(Long provId) {
        if (provId == null) {
            return null;
        }
        return provider.provId.eq(provId);

    }

    private BooleanExpression eqNextStockId(Long stockId) {
        if (stockId == null) {
            return null;
        }
        return storeStock.nextStock.stockId.eq(stockId);
    }

    private BooleanExpression eqGoodsId(Long goodsId) {
        if (goodsId == null) {
            return null;
        }
        return goods.goodsId.eq(goodsId);
    }

    private BooleanExpression eqInStockStatus(WmsEnum.InStockStatus inStockStatus) {
        if (inStockStatus == null) {
            return null;
        }
        return deviceStatus.inStockStatus.eq(inStockStatus);
    }

    private BooleanExpression eqCapacity(String capacity) {
        if (StringUtils.isEmpty(capacity)) {
            return null;
        }
        return goodsOption.capacity.eq(capacity);
    }

    private BooleanExpression eqColorName(String colorName) {
        if (StringUtils.isEmpty(colorName)) {
            return null;
        }
        return goodsOption.colorName.eq(colorName);
    }

    private BooleanExpression containsFullBarcode(String fullBarcode) {
        if (StringUtils.isEmpty(fullBarcode)) {
            return null;
        }
        return device.fullBarcode.contains(fullBarcode);
    }

    private BooleanExpression eqExtrrStatus(WmsEnum.DeviceExtrrStatus extrrStatus) {
        if (extrrStatus == null) {
            return null;
        }
        return deviceStatus.extrrStatus.eq(extrrStatus);
    }

    private BooleanExpression eqFaultyYn(String productFaultyYn) {
        if (StringUtils.isEmpty(productFaultyYn)) {
            return null;
        }
        return deviceStatus.productFaultyYn.eq(productFaultyYn);
    }

    private BooleanExpression eqStatusStr(WmsEnum.StockStatStr statusStr) {
        if (statusStr == null) {
            return null;
        }
        return inStock.statusStr.eq(statusStr);
    }

    private BooleanExpression eqDeliveryType(WmsEnum.DeliveryType DeliveryType) {
        if (DeliveryType == null) {
            return null;
        }
        return delivery.deliveryType.eq(DeliveryType);
    }

    private BooleanExpression eqDeliveryStatus(WmsEnum.DeliveryStatus deliveryStatus) {
        if (deliveryStatus == null) {
            return null;
        }
        return delivery.deliveryStatus.eq(deliveryStatus);
    }
}
























