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
                        , delivery.cusName.as("cusName")
                        , delivery.cusPhone.as("cusPhone")
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
                .orderBy(moveStock.regiDateTime.desc())
                .fetch();
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);
        List<MoveStockResponseDto> resultList = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public Page<TransResponseDto> getTransTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto requestDto) {
        JPQLQuery<TransResponseDto> query = getQuerydsl().createQuery();

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
                        , delivery.cusName.as("cusName")
                        , delivery.cusPhone.as("cusPhone")
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
                .orderBy(moveStock.regiDateTime.desc())
                .fetch();
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);
        List<TransResponseDto> resultList = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
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

