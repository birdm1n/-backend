package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.base.dto.PagingDto;
import com.daema.base.enums.StatusEnum;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.QDelivery;
import com.daema.wms.domain.QStock;
import com.daema.wms.domain.dto.request.DeviceCurrentRequestDto;
import com.daema.wms.domain.dto.response.DeviceCurrentResponseDto;
import com.daema.wms.domain.dto.response.DeviceHistoryResponseDto;
import com.daema.wms.domain.dto.response.DeviceListResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomDeviceRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.daema.base.domain.QMembers.members;
import static com.daema.commgmt.domain.QGoods.goods;
import static com.daema.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.commgmt.domain.QStore.store;
import static com.daema.wms.domain.QDelivery.delivery;
import static com.daema.wms.domain.QDevice.device;
import static com.daema.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.wms.domain.QInStock.inStock;
import static com.daema.wms.domain.QMoveStock.moveStock;
import static com.daema.wms.domain.QOpening.opening;
import static com.daema.wms.domain.QOutStock.outStock;
import static com.daema.wms.domain.QProvider.provider;
import static com.daema.wms.domain.QReturnStock.returnStock;
import static com.daema.wms.domain.QStoreStock.storeStock;

public class DeviceRepositoryImpl extends QuerydslRepositorySupport implements CustomDeviceRepository {

    public DeviceRepositoryImpl() {
        super(Device.class);
    }

    @PersistenceContext
    private EntityManager em;

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
                , inStock.inStockMemo.as("memo")
                )
        )
                .from(device)
                .innerJoin(device.inStocks, inStock)
                .on(device.dvcId.eq(dvcId).and(device.store.storeId.eq(storeId)))
                .innerJoin(inStock.store, store)
                .on(inStock.device.dvcId.eq(dvcId).and(inStock.store.storeId.eq(storeId)))
                .innerJoin(inStock.regiUserId, members)
                .on(inStock.device.dvcId.eq(dvcId).and(inStock.regiUserId.seq.eq(members.seq)));

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
                , delivery.deliveryMemo.as("memo")
                )
        )
                .from(device)
                .innerJoin(device.moveStockList, moveStock)
                .on(device.dvcId.eq(dvcId).and(device.store.storeId.eq(storeId)))
                .innerJoin(moveStock.store, store)
                .on(moveStock.device.dvcId.eq(dvcId).and(moveStock.store.storeId.eq(storeId)))
                .innerJoin(moveStock.regiUserId, members)
                .on(moveStock.device.dvcId.eq(dvcId).and(moveStock.regiUserId.seq.eq(members.seq)))
                .innerJoin(moveStock.delivery, delivery)
        ;

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
                , delivery.deliveryMemo.as("memo")
                )
        )
                .from(device)
                .innerJoin(device.outStocks, outStock)
                .on(device.dvcId.eq(dvcId).and(device.store.storeId.eq(storeId)))
                .innerJoin(outStock.store, store)
                .on(outStock.device.dvcId.eq(dvcId).and(outStock.store.storeId.eq(storeId)))
                .innerJoin(outStock.regiUserId, members)
                .on(outStock.device.dvcId.eq(dvcId).and(outStock.regiUserId.seq.eq(members.seq)))
                .innerJoin(outStock.delivery, delivery)
        ;

        historyList.addAll(outStockQuery.fetch());


        returnStockQuery.select(Projections.fields(
                DeviceHistoryResponseDto.class
                , Expressions.asString(WmsEnum.StockType.RETURN_STOCK.getStatusMsg()).as("stockTypeMsg")
                , returnStock.regiDateTime.as("regiDateTime")
                , returnStock.store.storeName.as("storeName")
                , returnStock.regiUserId.name.as("regiUserName")
                , returnStock.returnStockMemo.as("memo")
                )
        )
                .from(device)
                .innerJoin(device.returnStockList, returnStock)
                .on(device.dvcId.eq(dvcId).and(device.store.storeId.eq(storeId)))
                .innerJoin(returnStock.store, store)
                .on(returnStock.device.dvcId.eq(dvcId).and(returnStock.store.storeId.eq(storeId)))
                .innerJoin(returnStock.regiUserId, members)
                .on(returnStock.device.dvcId.eq(dvcId).and(returnStock.regiUserId.seq.eq(members.seq)))
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
        QDelivery moveDelivery = new QDelivery("moveDelivery");
        QDelivery outDelivery = new QDelivery("outDelivery");

        query.select(Projections.fields(
                DeviceCurrentResponseDto.class
                , device.dvcId.as("dvcId")
                , inStock.regiDateTime.as("inStockRegiDate")
                , storeStock.stockType.as("stockType")
                , storeStock.updDateTime.as("moveRegiDate") // storeStocke은 각 상태값들이 update기준으로 상태가 변경되기 때문에 출고일을 잡는다.
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockName.as("nextStockName")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")
                , goodsOption.capacity.as("capacity")
                , goodsOption.colorName.as("colorName")
                , device.rawBarcode.as("rawBarcode")
                , device.inStockAmt.as("inStockAmt")
                , deviceStatus.inStockStatus.as("inStockStatus")
                , deviceStatus.productFaultyYn.as("productFaultyYn")
                , deviceStatus.extrrStatus.as("extrrStatus")
                , deviceStatus.productMissYn.as("productMissYn")
                , deviceStatus.missProduct.as("missProduct")
                , deviceStatus.ddctAmt.as("ddctAmt")
                , deviceStatus.addDdctAmt.as("addDdctAmt")
                , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                , returnStock.returnStockAmt.as("returnStockAmt")
                , maker.codeNm.as("makerName")
                , telecom.codeNm.as("telecomName")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.deliveryType)
                        .otherwise(outDelivery.deliveryType).as("deliveryType")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.deliveryStatus)
                        .otherwise(outDelivery.deliveryStatus).as("deliveryStatus")
                , new CaseBuilder()
                        .when(moveStock.isNotNull())
                        .then(moveDelivery.cusName)
                        .otherwise(outDelivery.cusName).as("cusName")
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
                , new CaseBuilder()
                        .when(
                                opening.isNull().and(
                                        storeStock.stockType.in(WmsEnum.StockType.SELL_MOVE, WmsEnum.StockType.STOCK_MOVE)
                                )
                        ) /* 개통 데이터가 없으면서, 이동재고/판매이동 경우 개통 가능 - (미개통 상태)*/
                        .then(StatusEnum.FLAG_Y.getStatusMsg())
                        /* 이동재고/판매이동 상태가 아닌 경우 개통 불가능 - (-) */
                        .otherwise(StatusEnum.FLAG_N.getStatusMsg())
                        .as("openingYn")
                , new CaseBuilder()
                        .when(
                                opening.isNull().and(
                                        storeStock.stockType.in(WmsEnum.StockType.SELL_MOVE, WmsEnum.StockType.STOCK_MOVE)
                                )
                        ) /* 개통 데이터가 없으면서, 이동재고/판매이동 경우 개통 가능 - (미개통 상태)*/
                        .then(WmsEnum.OpeningText.NOT_OPENING.getStatusMsg())
                        .when(
                                opening.isNotNull().and(
                                        storeStock.stockType.in(WmsEnum.StockType.SELL_MOVE, WmsEnum.StockType.STOCK_MOVE)
                                )
                        )/* 개통 불가 가능 - (개통 상태)*/
                        .then(WmsEnum.OpeningText.OPENING.getStatusMsg())
                        /* 이동재고/판매이동 상태가 아닌 경우 개통 불가능 - (-) */
                        .otherwise(WmsEnum.OpeningText.NONE.getStatusMsg())
                        .as("openingText")
                , opening.openingDate.as("openingDate")
        ))
                .from(device)
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
                .leftJoin(device.openingList, opening).on
                (
                        opening.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()) /* 최종 상태가 => 개통, 개통 완료인 건만 조회 */
                )
                .leftJoin(moveStock).on
                (
                        storeStock.stockType.in(WmsEnum.StockType.SELL_MOVE, WmsEnum.StockType.STOCK_MOVE),
                        storeStock.stockTypeId.eq(moveStock.moveStockId)
                )
                .leftJoin(moveStock.delivery, moveDelivery)
                .leftJoin(outStock).on
                (
                        storeStock.stockType.in(WmsEnum.StockType.SELL_TRNS, WmsEnum.StockType.STOCK_TRNS, WmsEnum.StockType.FAULTY_TRNS),
                        storeStock.stockTypeId.eq(outStock.outStockId)
                )
                .leftJoin(outStock.delivery, outDelivery)
                .leftJoin(device.returnStockList, returnStock).on
                (
                        returnStock.delYn.eq("N")
                )
                .where(
                        eqMaker(requestDto.getMaker()),
                        eqTelecom(requestDto.getTelecom()),
                        eqProvId(requestDto.getProvId()),
                        eqNextStockId(requestDto.getNextStockId()),
                        eqInStockStatus(requestDto.getInStockStatus()),
                        eqStockType(requestDto.getStockType()),
                        eqGoodsId(requestDto.getGoodsId()),
                        eqModelName(requestDto.getModelName()),
                        eqCapacity(requestDto.getCapacity()),
                        eqColorName(requestDto.getColorName()),
                        containsBarcode(requestDto.getBarcode()),
                        eqFaultyYn(requestDto.getProductFaultyYn()),
                        eqExtrrStatus(requestDto.getExtrrStatus()),
                        eqMoveOrOutDeliveryType(requestDto.getDeliveryType(), moveDelivery, outDelivery),
                        eqMoveOrOutDeliveryStatus(requestDto.getDeliveryStatus(), moveDelivery, outDelivery),
                        betweenMoveStockRegDt(requestDto.getMoveRegiDate(), requestDto.getMoveRegiDate()),
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate())
                )
                .orderBy(device.regiDateTime.desc());


        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);


        QueryResults<DeviceCurrentResponseDto> resultList = query.fetchResults();
        return new PageImpl<>(resultList.getResults(), pageable, resultList.getTotal());
    }

    @Override
    public long deviceDuplCk(Store store, String barcode, List<Long> goodsOptionId ) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        long resultCount = queryFactory
                .selectFrom(device)
                .where(
                        device.store.storeId.eq(store.getStoreId()),
                        device.delYn.eq("N"),
                        device.rawBarcode.eq(barcode)
                        /*  rawBarcode 는 del_yn = 'N' 에서는 유니크.
                            다른 기기이지만 fullBarcode 는 뒷 1자리를 제거하기 때문에 중복으로 필터될 수 있음.
                            상품 옵션에 상관 없이 rawBarcode 겹치면 안됨. 20210622
                        device.rawBarcode.eq(barcode).or(
                                device.fullBarcode.eq(barcode).or(
                                        device.serialNo.eq(barcode)
                                )
                        ),
                        inGoodsOptionId(goodsOptionId)
                        */
                )
                .fetchCount();
        return resultCount;
    }

    @Override
    public Page<DeviceListResponseDto> getDeviceWithBarcode(String barcode, Store store) {

        JPQLQuery<DeviceListResponseDto> query = getQuerydsl().createQuery();
        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail network = new QCodeDetail("network");

        String subBarcode = StringUtils.hasText(barcode) && barcode.length() > 3
                ? barcode.substring(1, barcode.length() - 1)
                : barcode;

        query.select(Projections.fields(
                DeviceListResponseDto.class
                , device.dvcId.as("dvcId")
                , device.rawBarcode.as("rawBarcode")

                , telecom.codeNm.as("telecomName")
                , maker.codeNm.as("makerName")
                , network.codeNm.as("networkName")

                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")

                , goodsOption.colorName.as("colorName")
                , goodsOption.capacity.as("capacity")
                , goodsOption.unLockYn.as("unLockYn")
                )
        )
                .from(device)
                .innerJoin(device.goodsOption, goodsOption).on(
                    device.store.storeId.eq(store.getStoreId()),
                    device.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
                    device.rawBarcode.contains(barcode).or(
                            device.fullBarcode.contains(barcode).or(
                                    device.serialNo.contains(barcode)
                            )
                    ).or(
                        device.rawBarcode.contains(subBarcode).or(
                                device.fullBarcode.contains(subBarcode).or(
                                        device.serialNo.contains(subBarcode)
                                )
                        )
                    )
                )
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(telecom).on(
                goods.networkAttribute.telecom.eq(telecom.codeSeq)
        )
                .innerJoin(maker).on(
                goods.maker.eq(maker.codeSeq)
        )
                .innerJoin(network).on(
                goods.networkAttribute.network.eq(network.codeSeq)
        );

        query.orderBy(goods.goodsName.asc());

        PagingDto requestDto = new PagingDto();
        requestDto.setPageRange(10000);  //전체 데이터 가져오기 위함. 1개 관리점에 10000건의 바코드가 겹칠 일은 없음

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<DeviceListResponseDto> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
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

    private BooleanExpression eqStockType(WmsEnum.StockType stockType) {
        if (stockType == null) {
            return null;
        }
        return storeStock.stockType.eq(stockType);
    }

    private BooleanExpression eqMoveOrOutDeliveryType(WmsEnum.DeliveryType deliveryType, QDelivery moveDlvr, QDelivery outDlvr) {
        if (deliveryType == null) {
            return null;
        }

        return (moveDlvr.deliveryType.eq(deliveryType).or
                (outDlvr.deliveryType.eq(deliveryType)));
    }

    private BooleanExpression eqMoveOrOutDeliveryStatus(WmsEnum.DeliveryStatus deliveryStatus, QDelivery moveDlvr, QDelivery outDlvr) {
        if (deliveryStatus == null) {
            return null;
        }

        return (moveDlvr.deliveryStatus.eq(deliveryStatus).or(
                outDlvr.deliveryStatus.eq(deliveryStatus)));
    }

    private BooleanExpression inGoodsOptionId(List<Long> goodsOptionId) {
        if (goodsOptionId == null) {
            return null;
        }
        return device.goodsOption.goodsOptionId.in(goodsOptionId);
    }
}
























