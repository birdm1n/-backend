package com.daema.core.wms.repository;

import com.daema.core.base.domain.QCodeDetail;
import com.daema.core.base.domain.common.RetrieveClauseBuilder;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.wms.domain.QStock;
import com.daema.core.wms.domain.StoreStock;
import com.daema.core.wms.dto.request.StoreStockRequestDto;
import com.daema.core.wms.dto.response.DeviceStatusListDto;
import com.daema.core.wms.dto.response.StoreStockResponseDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.repository.custom.CustomStoreStockRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.daema.core.commgmt.domain.QGoods.goods;
import static com.daema.core.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.core.commgmt.domain.QStore.store;
import static com.daema.core.wms.domain.QDelivery.delivery;
import static com.daema.core.wms.domain.QDevice.device;
import static com.daema.core.wms.domain.QDeviceJudge.deviceJudge;
import static com.daema.core.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.core.wms.domain.QInStock.inStock;
import static com.daema.core.wms.domain.QMoveStock.moveStock;
import static com.daema.core.wms.domain.QMoveStockAlarm.moveStockAlarm;
import static com.daema.core.wms.domain.QOpening.opening;
import static com.daema.core.wms.domain.QOutStock.outStock;
import static com.daema.core.wms.domain.QProvider.provider;
import static com.daema.core.wms.domain.QReturnStock.returnStock;
import static com.daema.core.wms.domain.QStoreStock.storeStock;

public class StoreStockRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreStockRepository {

    public StoreStockRepositoryImpl() {
        super(StoreStock.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<StoreStockResponseDto> getStoreStockList(StoreStockRequestDto requestDto) {
        JPQLQuery<StoreStockResponseDto> query = getQuerydsl().createQuery();

        Path<LocalDateTime> compareCheckDateTime = Expressions.path(LocalDateTime.class, "compareCheckDateTime");

        QStock prevStock = new QStock("prevStock");
        QStock nextStock = new QStock("nextStock");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        query.select(Projections.fields(
                StoreStockResponseDto.class
                , storeStock.storeStockId.as("storeStockId")
                , storeStock.stockType.as("stockType")
                , storeStock.checkDateTime1.as("stockCheckDateTime1")
                , storeStock.checkDateTime2.as("stockCheckDateTime2")

                , device.dvcId.as("dvcId")
                , device.rawBarcode.as("rawBarcode")
                , device.inStockAmt.as("inStockAmt")

                , telecom.codeSeq.as("telecom")
                , telecom.codeNm.as("telecomName")
                , maker.codeSeq.as("maker")
                , maker.codeNm.as("makerName")

                , prevStock.stockId.as("prevStockId")
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockId.as("nextStockId")
                , nextStock.stockName.as("nextStockName")
                , new CaseBuilder()
                        .when(nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                        .then(WmsEnum.StockStatStr.I.getStatusMsg())
                        .otherwise(WmsEnum.StockStatStr.M.getStatusMsg()).as("statusStr")
                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")

                , goodsOption.goodsOptionId.as("goodsOptionId")
                , goodsOption.colorName.as("colorName")
                , goodsOption.commonBarcode.as("commonBarcode")
                , goodsOption.capacity.as("capacity")

                , inStock.inStockId.as("inStockId")
                , inStock.regiDateTime.as("inStockRegiDateTime")
                , inStock.inStockMemo.as("inStockMemo")

                , returnStock.returnStockId.as("returnStockId")
                , returnStock.returnStockAmt.as("returnStockAmt")
                , returnStock.returnStockMemo.as("returnStockMemo")
                , new CaseBuilder()
                        .when(storeStock.checkDateTime1.before(storeStock.checkDateTime2))
                        .then(storeStock.checkDateTime2)
                        .otherwise(storeStock.checkDateTime1)
                        .as(compareCheckDateTime)

                , Projections.fields(
                        DeviceStatusListDto.class
                        , deviceStatus.dvcStatusId.as("dvcStatusId")
                        , deviceStatus.inStockStatus.as("inStockStatus")
                        , deviceStatus.productFaultyYn.as("productFaultyYn")
                        , deviceStatus.productMissYn.as("productMissYn")
                        , deviceStatus.extrrStatus.as("extrrStatus")
                        , deviceStatus.ddctAmt.as("ddctAmt")
                        , deviceStatus.addDdctAmt.as("addDdctAmt")
                        , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                        , deviceStatus.missProduct.as("missProduct")
                ).as("deviceStatusListDto")

                )
        )

                .from(storeStock)
                .innerJoin(storeStock.device, device).on(
                storeStock.device.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
                        .and(storeStock.stockYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
        )
                .innerJoin(device.deviceStatusList, deviceStatus).on(
                device.dvcId.eq(deviceStatus.device.dvcId)
                        .and(deviceStatus.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
        )
                .innerJoin(storeStock.store, store).on(
                store.storeId.eq(requestDto.getStoreId())
        )
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(maker).on(
                goods.maker.eq(maker.codeSeq)
        )
                .innerJoin(telecom).on(
                goods.networkAttribute.telecom.eq(telecom.codeSeq)
        )
                .leftJoin(storeStock.prevStock, prevStock)
                .innerJoin(storeStock.nextStock, nextStock)
                .leftJoin(inStock).on(
                inStock.device.dvcId.eq(storeStock.device.dvcId))
                .leftJoin(returnStock).on(
                returnStock.device.dvcId.eq(storeStock.device.dvcId)
                        .and(returnStock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
        )
                .leftJoin(device.openingList, opening).on(
                        opening.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
        )
                .where(
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        betweenStoreStockCheckDt(requestDto.getStoreStockCheckDate(), requestDto.getStoreStockCheckDate()),
                        eqNextStockId(requestDto.getNextStockId()),
                        eqStatusStr(nextStock, requestDto.getStatusStr()),
                        eqInStockStatus(requestDto.getInStockStatus()),
                        containsBarcode(requestDto.getBarcode()),
                        eqFaultyYn(requestDto.getProductFaultyYn()),
                        eqExtrrStatus(requestDto.getExtrrStatus()),
                        eqColorName(requestDto.getColorName()),
                        eqGoodsId(requestDto.getGoodsId()),
                        eqCapacity(requestDto.getCapacity()),
                        eqTelecom(requestDto.getTelecom()),
                        eqMaker(requestDto.getMaker()),
                        opening.openingId.isNull()
                )
                .orderBy(
                        new OrderSpecifier(Order.DESC, compareCheckDateTime).nullsLast()
                        , storeStock.regiDateTime.desc()
                );

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<StoreStockResponseDto> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public Page<StoreStockResponseDto> getLongTimeStoreStockList(StoreStockRequestDto requestDto) {
        JPQLQuery<StoreStockResponseDto> query = getQuerydsl().createQuery();

        QStock prevStock = new QStock("prevStock");
        QStock nextStock = new QStock("nextStock");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        query.select(Projections.fields(
                StoreStockResponseDto.class
                , storeStock.storeStockId.as("storeStockId")
                , storeStock.stockType.as("stockType")

                , moveStock.regiDateTime.as("moveDateTime")

                , device.dvcId.as("dvcId")
                , device.rawBarcode.as("rawBarcode")
                , device.inStockAmt.as("inStockAmt")

                , telecom.codeSeq.as("telecom")
                , telecom.codeNm.as("telecomName")
                , maker.codeSeq.as("maker")
                , maker.codeNm.as("makerName")

                , prevStock.stockId.as("prevStockId")
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockId.as("nextStockId")
                , nextStock.stockName.as("nextStockName")
                , new CaseBuilder()
                        .when(nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                        .then(WmsEnum.StockStatStr.I.getStatusMsg())
                        .otherwise(WmsEnum.StockStatStr.M.getStatusMsg()).as("statusStr")
                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")

                , goodsOption.goodsOptionId.as("goodsOptionId")
                , goodsOption.colorName.as("colorName")
                , goodsOption.commonBarcode.as("commonBarcode")
                , goodsOption.capacity.as("capacity")

                , inStock.inStockId.as("inStockId")
                , inStock.regiDateTime.as("inStockRegiDateTime")
                , inStock.inStockMemo.as("inStockMemo")

                , returnStock.returnStockId.as("returnStockId")
                , returnStock.returnStockAmt.as("returnStockAmt")
                , returnStock.returnStockMemo.as("returnStockMemo")

                , Projections.fields(
                        DeviceStatusListDto.class
                        , deviceStatus.dvcStatusId.as("dvcStatusId")
                        , deviceStatus.inStockStatus.as("inStockStatus")
                        , deviceStatus.productFaultyYn.as("productFaultyYn")
                        , deviceStatus.productMissYn.as("productMissYn")
                        , deviceStatus.extrrStatus.as("extrrStatus")
                        , deviceStatus.ddctAmt.as("ddctAmt")
                        , deviceStatus.addDdctAmt.as("addDdctAmt")
                        , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                        , deviceStatus.missProduct.as("missProduct")
                ).as("deviceStatusListDto")
                )
        )

                .from(storeStock)
                .innerJoin(storeStock.store, store).on(
                store.storeId.eq(requestDto.getStoreId())
        )
                .innerJoin(moveStockAlarm).on(
                moveStockAlarm.store.storeId.eq(store.storeId)
        )
                .innerJoin(storeStock.device, device).on(
                storeStock.device.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
                        .and(storeStock.stockYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
        )
                .innerJoin(device.deviceStatusList, deviceStatus).on(
                device.dvcId.eq(deviceStatus.device.dvcId)
                        .and(deviceStatus.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
        )
                .innerJoin(moveStock).on(
                storeStock.stockTypeId.eq(moveStock.moveStockId)
                        .and(storeStock.stockType.eq(WmsEnum.StockType.STOCK_MOVE))
        )
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(maker).on(
                goods.maker.eq(maker.codeSeq)
        )
                .innerJoin(telecom).on(
                goods.networkAttribute.telecom.eq(telecom.codeSeq)
        )

                .leftJoin(storeStock.prevStock, prevStock)
                .innerJoin(storeStock.nextStock, nextStock)

                .leftJoin(inStock).on(
                inStock.device.dvcId.eq(storeStock.device.dvcId))
                .leftJoin(returnStock).on(
                returnStock.device.dvcId.eq(storeStock.device.dvcId)
                        .and(returnStock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
        )
                .where(
                        moveStockAlarm.undeliveredDay.loe(
                                Expressions.dateTemplate(LocalDate.class
                                        , "DATEDIFF({0}, {1})"
                                        , LocalDate.now(), moveStock.regiDateTime).castToNum(Integer.class)
                        ),
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        betweenMoveStockRegDt(requestDto.getMoveStockRegiDate(), requestDto.getMoveStockRegiDate()),
                        eqNextStockId(requestDto.getNextStockId()),
                        eqStatusStr(nextStock, requestDto.getStatusStr()),
                        eqInStockStatus(requestDto.getInStockStatus()),
                        containsBarcode(requestDto.getBarcode()),
                        eqFaultyYn(requestDto.getProductFaultyYn()),
                        eqExtrrStatus(requestDto.getExtrrStatus()),
                        eqColorName(requestDto.getColorName()),
                        eqGoodsId(requestDto.getGoodsId()),
                        eqCapacity(requestDto.getCapacity()),
                        eqTelecom(requestDto.getTelecom()),
                        eqMaker(requestDto.getMaker())
                );

        if(!StringUtils.hasText(requestDto.getOrderMoveDate())
            || TypeEnum.ASC.getStatusMsg().equals(requestDto.getOrderMoveDate())){
            query.orderBy(moveStock.regiDateTime.asc());
        }else{
            query.orderBy(moveStock.regiDateTime.desc());
        }

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<StoreStockResponseDto> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public Page<StoreStockResponseDto> getFaultyStoreStockList(StoreStockRequestDto requestDto) {
        JPQLQuery<StoreStockResponseDto> query = getQuerydsl().createQuery();

        QStock prevStock = new QStock("prevStock");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        query.select(Projections.fields(
                StoreStockResponseDto.class
                , storeStock.storeStockId.as("storeStockId")
                , storeStock.stockType.as("stockType")

                , outStock.regiDateTime.as("moveDateTime")

                , device.dvcId.as("dvcId")
                , device.rawBarcode.as("rawBarcode")
                , device.inStockAmt.as("inStockAmt")

                , telecom.codeSeq.as("telecom")
                , telecom.codeNm.as("telecomName")
                , maker.codeSeq.as("maker")
                , maker.codeNm.as("makerName")

                , provider.provId.as("provId")
                , provider.provName.as("provName")

                , prevStock.stockId.as("stockId")
                , prevStock.stockName.as("stockName")
                , new CaseBuilder()
                        .when(prevStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                        .then(WmsEnum.StockStatStr.I.getStatusMsg())
                        .otherwise(WmsEnum.StockStatStr.M.getStatusMsg()).as("statusStr")

                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")

                , goodsOption.goodsOptionId.as("goodsOptionId")
                , goodsOption.colorName.as("colorName")
                , goodsOption.commonBarcode.as("commonBarcode")
                , goodsOption.capacity.as("capacity")

                , inStock.inStockId.as("inStockId")
                , inStock.regiDateTime.as("inStockRegiDateTime")
                , inStock.inStockMemo.as("inStockMemo")

                , returnStock.returnStockId.as("returnStockId")
                , returnStock.returnStockAmt.as("returnStockAmt")
                , returnStock.returnStockMemo.as("returnStockMemo")

                , delivery.deliveryType.as("deliveryType")
                , delivery.deliveryStatus.as("deliveryStatus")

                , deviceJudge.judgeMemo.as("judgeMemo")
                , deviceJudge.judgeStatus.as("judgeStatus")


                , Projections.fields(
                        DeviceStatusListDto.class
                        , deviceStatus.dvcStatusId.as("dvcStatusId")
                        , deviceStatus.inStockStatus.as("inStockStatus")
                        , deviceStatus.productFaultyYn.as("productFaultyYn")
                        , deviceStatus.productMissYn.as("productMissYn")
                        , deviceStatus.extrrStatus.as("extrrStatus")
                        , deviceStatus.ddctAmt.as("ddctAmt")
                        , deviceStatus.addDdctAmt.as("addDdctAmt")
                        , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                        , deviceStatus.missProduct.as("missProduct")
                ).as("deviceStatusListDto")
                )
        )

                .from(storeStock)
                .innerJoin(storeStock.store, store).on(
                store.storeId.eq(requestDto.getStoreId())
        )
                .innerJoin(storeStock.device, device)
                //이관 후, 기기 삭제 또는 내 재고 상태가 아닐 수 있음
                //storeStock.device.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
                //.and(storeStock.stockYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))

                .innerJoin(device.deviceStatusList, deviceStatus).on(
                device.dvcId.eq(deviceStatus.device.dvcId)
                        .and(deviceStatus.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
        )

                .innerJoin(outStock).on(
                storeStock.stockTypeId.eq(outStock.outStockId)
                        .and(storeStock.stockType.eq(WmsEnum.StockType.FAULTY_TRNS))
        )
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(maker).on(
                goods.maker.eq(maker.codeSeq)
        )
                .innerJoin(telecom).on(
                goods.networkAttribute.telecom.eq(telecom.codeSeq)
        )

                //현재보유처
                .innerJoin(storeStock.prevStock, prevStock)

                //공급처
                .innerJoin(provider).on(
                outStock.targetId.eq(provider.provId)
        )

                //배송
                .innerJoin(outStock.delivery, delivery)

                .leftJoin(deviceJudge).on(
                outStock.device.dvcId.eq(deviceJudge.device.dvcId)
                        .and(deviceJudge.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
                        )

        )

                .leftJoin(inStock).on(
                inStock.device.dvcId.eq(storeStock.device.dvcId))


                .leftJoin(returnStock).on(
                returnStock.device.dvcId.eq(storeStock.device.dvcId)
                        .and(returnStock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
        );

        query.where(
                betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                eqPrevStockId(requestDto.getPrevStockId()),
                eqProvId(requestDto.getProvId()),
                eqStatusStr(prevStock, requestDto.getStatusStr()),
                eqJudgeStatus(requestDto.getJudgeStatus()),
                eqDeliveryStatus(requestDto.getDeliveryStatus()),
                containsBarcode(requestDto.getBarcode()),
                eqFaultyYn(requestDto.getProductFaultyYn()),
                eqExtrrStatus(requestDto.getExtrrStatus()),
                eqColorName(requestDto.getColorName()),
                eqGoodsId(requestDto.getGoodsId()),
                eqCapacity(requestDto.getCapacity()),
                eqTelecom(requestDto.getTelecom()),
                eqMaker(requestDto.getMaker())
        )
                .orderBy(outStock.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<StoreStockResponseDto> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression eqTelecom(Long telecom) {
        if (telecom == null) {
            return null;
        }
        return goods.networkAttribute.telecom.eq(telecom);
    }

    private BooleanExpression eqMaker(Long maker) {
        if (maker == null) {
            return null;
        }
        return goods.maker.eq(maker);
    }

    private BooleanExpression eqPrevStockId(Long prevStockId) {
        if (prevStockId == null) {
            return null;
        }
        return storeStock.prevStock.stockId.eq(prevStockId);
    }

    private BooleanExpression eqNextStockId(Long nextStockId) {
        if (nextStockId == null) {
            return null;
        }
        return storeStock.nextStock.stockId.eq(nextStockId);
    }

    private BooleanExpression eqProvId(Long provId) {
        if (provId == null) {
            return null;
        }
        return inStock.provider.provId.eq(provId);
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

    private BooleanExpression containsBarcode(String barcode) {
        if(StringUtils.isEmpty(barcode)){
            return null;
        }
        return device.rawBarcode.contains(barcode).or(
                device.fullBarcode.contains(barcode).or(
                        device.serialNo.contains(barcode)
                )
        );
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

    private BooleanExpression eqStatusStr(QStock stock, WmsEnum.StockStatStr statusStr) {
        if (statusStr == null) {
            return null;
        }

        if (WmsEnum.StockStatStr.I == statusStr) {
            return stock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode());
        } else {
            return stock.stockType.ne(TypeEnum.STOCK_TYPE_I.getStatusCode());
        }
    }

    private BooleanExpression eqDeliveryStatus(WmsEnum.DeliveryStatus deliveryStatus) {
        if (deliveryStatus == null) {
            return null;
        }

        if (WmsEnum.DeliveryStatus.NONE == deliveryStatus) {
            return delivery.deliveryStatus.isNull();
        } else {
            return delivery.deliveryStatus.eq(deliveryStatus);
        }
    }

    private BooleanExpression eqJudgeStatus(WmsEnum.JudgementStatus judgeStatus) {
        if (judgeStatus == null) {
            return null;
        }

        if (WmsEnum.JudgementStatus.NONE == judgeStatus) {
            return deviceJudge.dvcJudgeId.isNull();
        } else {
            return deviceJudge.judgeStatus.eq(judgeStatus);
        }
    }

    private BooleanExpression betweenStoreStockCheckDt(String startDt, String endDt) {
        if (StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)) {
            return null;
        }

        return storeStock.checkDateTime1.between(
                RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
        ).or(
                storeStock.checkDateTime2.between(
                        RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                        RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
                )
        );
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
}