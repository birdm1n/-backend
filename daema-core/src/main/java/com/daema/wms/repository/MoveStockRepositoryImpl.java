package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.QMember;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.MoveStock;

import com.daema.wms.domain.QStock;
import com.daema.wms.domain.dto.request.MoveStockRequestDto;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.dto.response.TransResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomMoveStockRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.daema.commgmt.domain.QGoods.goods;
import static com.daema.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.commgmt.domain.QStore.store;
import static com.daema.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.wms.domain.QMoveStock.moveStock;
import static com.daema.wms.domain.QProvider.provider;
import static com.daema.wms.domain.QInStock.inStock;
import static com.daema.wms.domain.QStoreStock.storeStock;
import static com.daema.wms.domain.QOutStock.outStock;
import static com.daema.wms.domain.QDelivery.delivery;
import static com.daema.wms.domain.QDevice.device;

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
}

