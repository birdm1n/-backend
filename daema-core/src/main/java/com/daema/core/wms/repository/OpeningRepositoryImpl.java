package com.daema.core.wms.repository;

import com.daema.core.base.domain.QCodeDetail;
import com.daema.core.base.domain.common.RetrieveClauseBuilder;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.wms.domain.Opening;
import com.daema.core.wms.domain.QDelivery;
import com.daema.core.wms.domain.QStock;
import com.daema.core.wms.dto.request.OpeningCurrentRequestDto;
import com.daema.core.wms.dto.response.OpeningCurrentResponseDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static com.daema.core.commgmt.domain.QGoods.goods;
import static com.daema.core.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.core.commgmt.domain.QStore.store;
import static com.daema.core.wms.domain.QDevice.device;
import static com.daema.core.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.core.wms.domain.QInStock.inStock;
import static com.daema.core.wms.domain.QMoveStock.moveStock;
import static com.daema.core.wms.domain.QOpening.opening;
import static com.daema.core.wms.domain.QOutStock.outStock;
import static com.daema.core.wms.domain.QReturnStock.returnStock;
import static com.daema.core.wms.domain.QStoreStock.storeStock;

public class OpeningRepositoryImpl extends QuerydslRepositorySupport implements CustomOpeningRepository {

    public OpeningRepositoryImpl() {
        super(Opening.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<OpeningCurrentResponseDto> getOpeningCurrentList(OpeningCurrentRequestDto requestDto) {

        JPQLQuery<OpeningCurrentResponseDto> query = getQuerydsl().createQuery();

        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail maker = new QCodeDetail("maker");
        QStock prevStock = new QStock("prevStock");
        QStock nextStock = new QStock("nextStock");
        QDelivery moveDelivery = new QDelivery("moveDelivery");
        QDelivery outDelivery = new QDelivery("outDelivery");
        query.select(Projections.fields(
                OpeningCurrentResponseDto.class
                , opening.openingId.as("openingId")
                , telecom.codeNm.as("telecomName")
                , device.dvcId.as("dvcId")
                , inStock.regiDateTime.as("inStockRegiDate")
                , opening.openingDate.as("openingDate")
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockName.as("nextStockName")
                , storeStock.stockType.as("stockType")
                , maker.codeNm.as("makerName")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")
                , goodsOption.capacity.as("capacity")
                , goodsOption.colorName.as("colorName")
                , device.rawBarcode.as("rawBarcode")
                , device.inStockAmt.as("inStockAmt")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.cusName)
                        .otherwise(outDelivery.cusName).as("cusName")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.cusPhone)
                        .otherwise(outDelivery.cusPhone).as("cusPhone")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.cusPhone1)
                        .otherwise(outDelivery.cusPhone1).as("cusPhone1")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.cusPhone2)
                        .otherwise(outDelivery.cusPhone2).as("cusPhone2")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.cusPhone3)
                        .otherwise(outDelivery.cusPhone3).as("cusPhone3")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.addr1)
                        .otherwise(outDelivery.addr1).as("addr1")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.addr2)
                        .otherwise(outDelivery.addr2).as("addr2")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.zipCode)
                        .otherwise(outDelivery.zipCode).as("zipCode")
                , opening.openingStatus.as("cancelStatus")
                , opening.cancelDate.as("cancelDate")
                , opening.cancelMemo.as("cancelMemo")
                , deviceStatus.inStockStatus.as("inStockStatus")
                , deviceStatus.productFaultyYn.as("productFaultyYn")
                , deviceStatus.extrrStatus.as("extrrStatus")
                , deviceStatus.productMissYn.as("productMissYn")
                , deviceStatus.missProduct.as("missProduct")
                , deviceStatus.ddctAmt.as("ddctAmt")
                , deviceStatus.addDdctAmt.as("addDdctAmt")
                , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                , returnStock.returnStockAmt.as("returnStockAmt")

        ))
                .from(opening)
                .innerJoin(opening.device, device)
                .innerJoin(device.store, store).on
                (
                        store.storeId.eq(requestDto.getStoreId())
                )
                .innerJoin(device.inStocks, inStock)
                .innerJoin(device.deviceStatusList, deviceStatus).on
                (
                        deviceStatus.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
                )
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(maker).on
                (
                        goods.maker.eq(maker.codeSeq)
                )
                .innerJoin(telecom).on
                (
                        goods.networkAttribute.telecom.eq(telecom.codeSeq)
                )
                .innerJoin(device.storeStock, storeStock)
                .leftJoin(storeStock.prevStock, prevStock)
                .leftJoin(storeStock.nextStock, nextStock)
                .leftJoin(moveStock).on
                (
                        storeStock.stockType.in(WmsEnum.StockType.SELL_MOVE, WmsEnum.StockType.STOCK_MOVE),
                        storeStock.stockTypeId.eq(moveStock.moveStockId)
                )
                .leftJoin(moveStock.delivery, moveDelivery)
                .leftJoin(outStock).on
                (
                        storeStock.stockType.in(WmsEnum.StockType.SELL_TRNS, WmsEnum.StockType.STOCK_TRNS, WmsEnum.StockType.FAULTY_TRNS, WmsEnum.StockType.RETURN_TRNS),
                        storeStock.stockTypeId.eq(outStock.outStockId)
                )
                .leftJoin(outStock.delivery, outDelivery)
                .leftJoin(device.returnStockList, returnStock).on
                (
                        returnStock.delYn.eq("N")
                )
                .where(
                        eqTelecom(requestDto.getTelecom()),
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        eqOpeningDate(requestDto.getOpeningDate()),
                        eqNextStockId(requestDto.getStockId()),
                        eqStockType(requestDto.getStockType()),
                        eqMaker(requestDto.getMaker()),
                        eqGoodsId(requestDto.getGoodsId()),
                        eqCapacity(requestDto.getCapacity()),
                        eqColorName(requestDto.getColorName()),
                        eqOpeningStatus(requestDto.getOpeningStatus()),
                        containCusName(requestDto.getCusName(), moveDelivery, outDelivery),
                        containCusPhone(requestDto.getCusPhone(), moveDelivery, outDelivery),
                        containsBarcode(requestDto.getBarcode())
                )
                .orderBy(opening.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);
        QueryResults<OpeningCurrentResponseDto> resultList = query.fetchResults();
        return new PageImpl<>(resultList.getResults(), pageable, resultList.getTotal());
    }


    private BooleanExpression containCusName(String data, QDelivery moveDlvr, QDelivery outDlvr) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return (moveDlvr.cusName.contains(data).or(
                outDlvr.cusName.contains(data)));
    }
    private BooleanExpression containCusPhone(String data, QDelivery moveDlvr, QDelivery outDlvr) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return (moveDlvr.cusPhone.contains(data).or(
                outDlvr.cusPhone.contains(data)));
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

    private BooleanExpression eqOpeningDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        return opening.openingDate.eq(LocalDate.parse(date));
    }

    private BooleanExpression eqTelecom(Long name) {
        if (name == null) {
            return null;
        }
        return goods.networkAttribute.telecom.eq(name);
    }

    private BooleanExpression eqMaker(Long maker) {
        if (maker == null) {
            return null;
        }
        return goods.maker.eq(maker);
    }

    private BooleanExpression eqOpeningStatus(WmsEnum.OpeningStatus openingStatus) {
        if (openingStatus == null) {
            return null;
        }
        return opening.openingStatus.eq(openingStatus);

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

    private BooleanExpression containsBarcode(String barcode) {
        if (StringUtils.isEmpty(barcode)) {
            return null;
        }
        return device.rawBarcode.contains(barcode).or(
                device.fullBarcode.contains(barcode).or(
                        device.serialNo.contains(barcode)
                )
        );
    }
    private BooleanExpression eqStockType(WmsEnum.StockType stockType) {
        if (stockType == null) {
            return null;
        }
        return storeStock.stockType.eq(stockType);
    }
}
