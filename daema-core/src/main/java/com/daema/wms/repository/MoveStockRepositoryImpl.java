package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.QMember;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.MoveStock;
import com.daema.wms.domain.QStock;
import com.daema.wms.domain.dto.request.MoveMgmtRequestDto;
import com.daema.wms.domain.dto.request.MoveStockRequestDto;
import com.daema.wms.domain.dto.response.MoveMgmtResponseDto;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.dto.response.TransResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomMoveStockRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

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

public class MoveStockRepositoryImpl extends QuerydslRepositorySupport implements CustomMoveStockRepository {
    public MoveStockRepositoryImpl() {
        super(MoveStock.class);
    }

    @Override
    public Page<MoveStockResponseDto> getMoveTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto requestDto) {
        JPQLQuery<MoveStockResponseDto> query = getQuerydsl().createQuery();
        QMember regMember = new QMember("regMember");
        QMember updMember = new QMember("updMember");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        QStock prevStock = new QStock("prevStock"); // 현재보유처
        QStock nextStock = new QStock("nextStock"); // 다음보유처

        query.select(
                Projections.fields(
                        MoveStockResponseDto.class
                        , moveStock.moveStockId.as("moveStockId")
                        , moveStock.moveStockType.as("moveStockType")
                        , telecom.codeNm.as("telecomName")
                        , maker.codeNm.as("makerName")
                        , goods.goodsName.as("goodsName")
                        , goods.modelName.as("modelName")
                        , goodsOption.capacity.as("capacity")
                        , goodsOption.colorName.as("colorName")
                        , device.fullBarcode.as("fullBarcode")
                        , device.inStockAmt.as("inStockAmt")
                        , deviceStatus.inStockStatus.as("inStockStatus")
                        , deviceStatus.productFaultyYn.as("productFaultyYn")
                        , deviceStatus.extrrStatus.as("extrrStatus")
                        , prevStock.stockName.as("prevStockName")
                        , nextStock.stockName.as("nextStockName")
                        , inStock.statusStr.as("statusStr")
                        , inStock.regiDateTime.as("inStockRegiDateTime")
                        , delivery.deliveryType.as("deliveryType")
                        , delivery.cusName.as("cusName")
                        , delivery.cusPhone.as("cusPhone")
                        , delivery.zipCode.as("zipCode")
                        , delivery.addr1.as("addr1")
                        , delivery.addr2.as("addr2")
                        , delivery.deliveryMemo.as("deliveryMemo")
                        , regMember.seq.as("regiUserId")
                        , regMember.username.as("regiUserName")
                        , moveStock.regiDateTime.as("regiDateTime")
                        , updMember.seq.as("updUserId")
                        , updMember.username.as("updUserName")
                )
        )
                .from(moveStock)
                .innerJoin(moveStock.store, store).on
                (
                        store.storeId.eq(requestDto.getStoreId())
                )
                .innerJoin(moveStock.regiUserId, regMember)
                .innerJoin(moveStock.updUserId, updMember)
                .innerJoin(moveStock.device, device).on
                (
                        moveStock.moveStockType.eq(WmsEnum.MoveStockType.valueOf(movePathType.name()))
                        , moveStock.delYn.eq("N")
                )
                .innerJoin(device.inStocks, inStock).on
                (
                        inStock.delYn.eq("N")
                )
                .innerJoin(device.deviceStatusList, deviceStatus).on
                (
                        deviceStatus.delYn.eq("N")
                )
                .innerJoin(moveStock.delivery, delivery)
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
                .leftJoin(moveStock.nextStock, nextStock)
                .leftJoin(moveStock.prevStock, prevStock)
                .orderBy(moveStock.regiDateTime.desc());
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);
        QueryResults<MoveStockResponseDto> resultList = query.fetchResults();
        return new PageImpl<>(resultList.getResults(), pageable, resultList.getTotal());
    }

    @Override
    public Page<TransResponseDto> getTransTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto requestDto) {
        JPQLQuery<TransResponseDto> query = getQuerydsl().createQuery();

        QMember regMember = new QMember("regMember");
        QMember updMember = new QMember("updMember");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        QStock prevStock = new QStock("prevStock"); // 현재보유처
        query.select(
                Projections.fields(
                        TransResponseDto.class
                        , outStock.outStockId.as("outStockId")
                        , outStock.outStockType.as("outStockType")
                        , outStock.regiDateTime.as("regiDateTime")
                        , telecom.codeNm.as("telecomName")
                        , maker.codeNm.as("makerName")
                        , goods.goodsName.as("goodsName")
                        , goods.modelName.as("modelName")
                        , goodsOption.capacity.as("capacity")
                        , goodsOption.colorName.as("colorName")
                        , device.fullBarcode.as("fullBarcode")
                        , device.inStockAmt.as("inStockAmt")
                        , deviceStatus.inStockStatus.as("inStockStatus")
                        , deviceStatus.productFaultyYn.as("productFaultyYn")
                        , deviceStatus.extrrStatus.as("extrrStatus")
                        , prevStock.stockName.as("prevStockName")
                        , store.storeName.as("nextStoreName")
                        , inStock.statusStr.as("statusStr")
                        , inStock.regiDateTime.as("inStockRegiDateTime")
                        , delivery.cusName.as("cusName")
                        , delivery.cusPhone.as("cusPhone")
                        , delivery.deliveryMemo.as("deliveryMemo")
                        , regMember.seq.as("regiUserId")
                        , regMember.username.as("regiUserName")
                        , updMember.seq.as("updUserId")
                        , updMember.username.as("updUserName")
                )
        )
                .from(outStock)
                .innerJoin(outStock.store, store).on
                (
                        store.storeId.eq(requestDto.getStoreId())
                )
                .innerJoin(outStock.regiUserId, regMember)
                .innerJoin(outStock.updUserId, updMember)
                .innerJoin(outStock.device, device).on
                (
                        outStock.outStockType.eq(WmsEnum.OutStockType.valueOf(movePathType.name()))
                        , outStock.delYn.eq("N")
                )
                .innerJoin(device.inStocks, inStock).on
                (
                        inStock.delYn.eq("N")
                )
                .innerJoin(inStock.provider, provider)
                .innerJoin(device.deviceStatusList, deviceStatus).on
                (
                        deviceStatus.delYn.eq("N")
                )
                .innerJoin(outStock.delivery, delivery)
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
                .leftJoin(outStock.prevStock, prevStock)
                .leftJoin(store).on
                (
                        store.storeId.eq(outStock.targetId)
                )
                .orderBy(outStock.regiDateTime.desc());
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        QueryResults<TransResponseDto> resultList = query.fetchResults();
        return new PageImpl<>(resultList.getResults(), pageable, resultList.getTotal());
    }

    @Override
    public Page<TransResponseDto> getFaultyTransTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto requestDto) {
        JPQLQuery<TransResponseDto> query = getQuerydsl().createQuery();

        QMember regMember = new QMember("regMember");
        QMember updMember = new QMember("updMember");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        QStock prevStock = new QStock("prevStock"); // 현재보유처
        query.select(
                Projections.fields(
                        TransResponseDto.class
                        , outStock.outStockId.as("outStockId")
                        , outStock.outStockType.as("outStockType")
                        , outStock.regiDateTime.as("regiDateTime")
                        , telecom.codeNm.as("telecomName")
                        , maker.codeNm.as("makerName")
                        , goods.goodsName.as("goodsName")
                        , goods.modelName.as("modelName")
                        , goodsOption.capacity.as("capacity")
                        , goodsOption.colorName.as("colorName")
                        , device.fullBarcode.as("fullBarcode")
                        , device.inStockAmt.as("inStockAmt")
                        , deviceStatus.inStockStatus.as("inStockStatus")
                        , deviceStatus.productFaultyYn.as("productFaultyYn")
                        , deviceStatus.extrrStatus.as("extrrStatus")
                        , prevStock.stockName.as("prevStockName")
                        , provider.provName.as("nextProvName")
                        , inStock.statusStr.as("statusStr")
                        , inStock.regiDateTime.as("inStockRegiDateTime")
                        , delivery.cusName.as("cusName")
                        , delivery.cusPhone.as("cusPhone")
                        , delivery.deliveryMemo.as("deliveryMemo")
                        , regMember.seq.as("regiUserId")
                        , regMember.username.as("regiUserName")
                        , updMember.seq.as("updUserId")
                        , updMember.username.as("updUserName")
                )
        )
                .from(outStock)
                .innerJoin(outStock.store, store).on
                (
                        store.storeId.eq(requestDto.getStoreId())
                )
                .innerJoin(outStock.regiUserId, regMember)
                .innerJoin(outStock.updUserId, updMember)
                .innerJoin(outStock.device, device).on
                (
                        outStock.outStockType.eq(WmsEnum.OutStockType.valueOf(movePathType.name()))
                        , outStock.delYn.eq("N")
                )
                .innerJoin(device.inStocks, inStock).on
                (
                        inStock.delYn.eq("N")
                )
                .innerJoin(inStock.provider, provider)
                .innerJoin(device.deviceStatusList, deviceStatus).on
                (
                        deviceStatus.delYn.eq("N")
                )
                .innerJoin(outStock.delivery, delivery)
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
                .leftJoin(outStock.prevStock, prevStock)
                .orderBy(outStock.regiDateTime.desc());
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        // 기존 로직은 select 먼저 콜 =>  카운트 순서로 진행  (성능 이슈로 아래와 같이 변경 필요)
        // - 카운트를 먼저 콜
        // - count = 0 이면 select 하지 않는 것 같음(확인 필요)
        // - fetchCount()와 fetchResults() 복잡한쿼리에 문제점 있음  https://www.inflearn.com/questions/23280
//        List<TransResponseDto> resultList = query.fetch();
//        long total = query.fetchCount();
//        return new PageImpl<>(resultList, pageable, total);

        QueryResults<TransResponseDto> resultList = query.fetchResults();
        return new PageImpl<>(resultList.getResults(), pageable, resultList.getTotal());
    }

    @Override
    public List<Store> getTransStoreList(long storeId) {
        JPQLQuery<Store> query = getQuerydsl().createQuery();
        return query.select(store)
                .from(store)
                .where(
                        store.useYn.eq("Y"),
                        store.storeId.ne(storeId)
                )
                .fetch();
    }

    @Override
    public Page<MoveMgmtResponseDto> getMoveMgmtList(MoveMgmtRequestDto requestDto) {
        JPQLQuery<MoveMgmtResponseDto> query = getQuerydsl().createQuery();

        QStock prevStock = new QStock("prevStock"); // 쿼리타입 사용, 직접 jpql생성 별칭 "prevStock" 현재 보유처
        QStock nextStock = new QStock("nextStock");

        QMember regMember = new QMember("regMember");
        QMember updMember = new QMember("updMember");

        QCodeDetail telecom = new QCodeDetail("telecom");
        QCodeDetail maker = new QCodeDetail("maker");


        query.select(Projections.fields(
                MoveMgmtResponseDto.class
                , moveStock.moveStockId.as("moveStockId")
                , telecom.codeNm.as("telecomName")
                , inStock.regiDateTime.as("inStockRegiDateTime") //입고일
                , moveStock.regiDateTime.as("moveRegiDateTime")  // 이동일
                , prevStock.stockId.as("prevStockId")
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockId.as("nextStockId")
                , nextStock.stockName.as("nextStockName")
                , moveStock.moveStockType.as("moveStockType") // 재고구분
                , returnStock.returnStockAmt.as("returnStockAmt") // 반품비 ////****
                , device.dvcId.as("dvcId")
                , maker.codeNm.as("makerName")
                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")
                , goodsOption.capacity.as("capacity")
                , goodsOption.goodsOptionId.as("goodsOptionId")
                , goodsOption.colorName.as("colorName")
                , goodsOption.commonBarcode.as("commonBarcode")
                , device.fullBarcode.as("fullBarcode")
                , device.inStockAmt.as("inStockAmt")
                , inStock.statusStr.as("statusStr")
                , deviceStatus.inStockStatus.as("inStockStatus") // 입고상태 : 정상, 개봉
                , deviceStatus.productFaultyYn.as("productFaultyYn") // 제품상태 : N(-), Y(불량)
                , deviceStatus.extrrStatus.as("extrrStatus") // 외장상태 : T : 상, M : 중, B : 하, F:파손
                , deviceStatus.productMissYn.as("productMissYn") // 구성품 누락 여부
                , deviceStatus.missProduct.as("missProduct")
                , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                , deviceStatus.addDdctAmt.as("addDdctAmt")
                , delivery.deliveryType.as("deliveryType") // 배송방법 : 택배,퀵,직접전달
                , delivery.deliveryStatus.as("deliveryStatus") //배송상태 : 배송중
                , regMember.seq.as("regiUserId")
                , regMember.username.as("regiUserName")
        ))
                .from(moveStock)
                .innerJoin(moveStock.store, store).on
                (
                        store.storeId.eq(requestDto.getStoreId())
                )
                .innerJoin(moveStock.regiUserId, regMember)
                .innerJoin(moveStock.updUserId, updMember)
                .innerJoin(moveStock.prevStock, prevStock)
                .innerJoin(moveStock.nextStock, nextStock)
                .innerJoin(moveStock.delivery, delivery)
                .innerJoin(moveStock.device, device)
                .innerJoin(device.inStocks, inStock).on
                (
                        inStock.delYn.eq("N")
                )
                .innerJoin(device.deviceStatusList, deviceStatus).on
                (
                        deviceStatus.delYn.eq("N")
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
                .leftJoin(device.returnStockList, returnStock).on
                (
                        returnStock.delYn.eq("N")
                )
                .where(
                        eqTelecom(requestDto.getTelecom()),
                        betweenInstockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        betweenMoveStockRegDt(requestDto.getMoveRegiDate(), requestDto.getMoveRegiDate()),
                        eqPrevStockId(requestDto.getPrevStockId()),//
                        eqNextStockId(requestDto.getNextStockId()),
                        eqMoveStockType(requestDto.getMoveStockType()),
                        eqMaker(requestDto.getMaker()),
                        eqCapacity(requestDto.getCapacity()),
                        eqColorName(requestDto.getColorName()),
                        eqInStockStatus(requestDto.getInStockStatus()),
                        eqFaultyYn(requestDto.getProductFaultyYn()),
                        eqExtrrStatus(requestDto.getExtrrStatus()),
                        eqDeliveryType(requestDto.getDeliveryType()),
                        eqDeliveryStatus(requestDto.getDeliveryStatus())
                )
                .orderBy(moveStock.regiDateTime.desc());
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);
        QueryResults<MoveMgmtResponseDto> resultList = query.fetchResults();
        return new PageImpl<>(resultList.getResults(), pageable, resultList.getTotal());
    }

    private BooleanExpression eqDeliveryStatus(WmsEnum.DeliveryStatus deliveryStatus) {
        if (deliveryStatus == null) {
            return null;
        }
        return delivery.deliveryStatus.eq(deliveryStatus);
    }

    private BooleanExpression eqDeliveryType(WmsEnum.DeliveryType deliveryType) {
        if (deliveryType == null) {
            return null;
        }

        return delivery.deliveryType.eq(deliveryType);
    }


    private BooleanExpression eqPrevStockId(Long prevstockId) {
        if (prevstockId == null) {
            return null;
        }
        return inStock.stock.stockId.eq(prevstockId);
    }

    private BooleanExpression eqNextStockId(Long nextstockId) {
        if (nextstockId == null) {
            return null;
        }
        return inStock.stock.stockId.eq(nextstockId);
    }

    private BooleanExpression eqMoveStockType(WmsEnum.MoveStockType moveStockType) {
        if (moveStockType == null) {
            return null;
        }
        return moveStock.moveStockType.eq(moveStockType);
    }

    private BooleanExpression containsFullBarcode(String fullBarcode) {
        if (StringUtils.isEmpty(fullBarcode)) {
            return null;
        }
        return device.fullBarcode.contains(fullBarcode);
    }

    private BooleanExpression eqColorName(String colorName) {
        if (StringUtils.isEmpty(colorName)) {
            return null;
        }
        return goodsOption.colorName.eq(colorName);
    }

    private BooleanExpression eqFaultyYn(String productFaultyYn) {
        if (StringUtils.isEmpty(productFaultyYn)) {
            return null;
        }
        return deviceStatus.productFaultyYn.eq(productFaultyYn);
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

    private BooleanExpression eqExtrrStatus(WmsEnum.DeviceExtrrStatus extrrStatus) {
        if (extrrStatus == null) {
            return null;
        }
        return deviceStatus.extrrStatus.eq(extrrStatus);
    }

    private BooleanExpression eqTelecom(Integer telecom) {
        if (telecom == null) {
            return null;
        }
        return goods.networkAttribute.telecom.eq(telecom);
    }

    private BooleanExpression eqMaker(Integer maker) {
        if (maker == null) {
            return null;
        }
        return goods.maker.eq(maker);
    }


    private BooleanExpression eqInStockStatus(WmsEnum.InStockStatus inStockStatus) {
        if (inStockStatus == null) {
            return null;
        }
        return deviceStatus.inStockStatus.eq(inStockStatus);
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

    private BooleanExpression betweenInstockRegDt(String startDt, String endDt) {
        if (StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)) {
            return null;
        }
        return inStock.regiDateTime.between(
                RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
        );
    }

}

