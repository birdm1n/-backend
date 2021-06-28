package com.daema.core.wms.repository;

import com.daema.core.base.domain.QCodeDetail;
import com.daema.core.base.domain.QMembers;
import com.daema.core.base.domain.common.RetrieveClauseBuilder;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.wms.domain.QStock;
import com.daema.core.wms.domain.ReturnStock;
import com.daema.core.wms.dto.request.ReturnStockRequestDto;
import com.daema.core.wms.dto.response.DeviceStatusResDto;
import com.daema.core.wms.dto.response.ReturnStockResDto;
import com.daema.core.wms.dto.response.ReturnStockResponseDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.repository.custom.CustomReturnStockRepository;
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

import java.util.List;

import static com.daema.core.commgmt.domain.QGoods.goods;
import static com.daema.core.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.core.commgmt.domain.QStore.store;
import static com.daema.core.wms.domain.QDevice.device;
import static com.daema.core.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.core.wms.domain.QInStock.inStock;
import static com.daema.core.wms.domain.QReturnStock.returnStock;
import static com.daema.core.wms.domain.QStoreStock.storeStock;


public class ReturnStockRepositoryImpl extends QuerydslRepositorySupport implements CustomReturnStockRepository {

    public ReturnStockRepositoryImpl() {
        super(ReturnStock.class);
    }

    @Override
    public Page<ReturnStockResponseDto> getSearchPage(ReturnStockRequestDto requestDto) {

        JPQLQuery<ReturnStockResponseDto> query = getQuerydsl().createQuery();
        QMembers regMember = new QMembers("regMember");
        QStock prevStock = new QStock("prevStock");
        QStock nextStock = new QStock("nextStock");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        query.select(Projections.fields(
                ReturnStockResponseDto.class
                , returnStock.returnStockId.as("returnStockId")
                , device.dvcId.as("dvcId")
                , deviceStatus.ddctAmt.as("ddctAmt")
                , deviceStatus.addDdctAmt.as("addDdctAmt")
                , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                , deviceStatus.missProduct.as("missProduct")
                , returnStock.returnStockMemo.as("returnStockMemo")
                , telecom.codeSeq.as("telecom")
                , telecom.codeNm.as("telecomName")
                , prevStock.stockId.as("prevStockId")
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockId.as("nextStockId")
                , nextStock.stockName.as("nextStockName")
                , new CaseBuilder()
                        .when(returnStock.nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                        .then(WmsEnum.StockStatStr.I.getStatusMsg())
                        .otherwise(WmsEnum.StockStatStr.M.getStatusMsg()).as("statusStr")
                , maker.codeSeq.as("maker")
                , maker.codeNm.as("makerName")
                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")
                , goodsOption.capacity.as("capacity")
                , goodsOption.goodsOptionId.as("goodsOptionId")
                , goodsOption.colorName.as("colorName")
                , goodsOption.commonBarcode.as("commonBarcode")
                , device.rawBarcode.as("rawBarcode")
                , returnStock.returnStockAmt.as("returnStockAmt")
                , deviceStatus.inStockStatus.as("returnStockStatus")
                , deviceStatus.productFaultyYn.as("productFaultyYn")
                , deviceStatus.productMissYn.as("productMissYn")
                , deviceStatus.extrrStatus.as("extrrStatus")
                , returnStock.regiDateTime.as("regiDateTime")
                , regMember.seq.as("regiUserId")
                , regMember.username.as("regiUserName")
                , inStock.regiDateTime.as("inStockRegiDateTime")
        ))
                .from(returnStock)
                .innerJoin(returnStock.regiUserId, regMember)
                .innerJoin(returnStock.device, device)
                .innerJoin(returnStock.returnDeviceStatus, deviceStatus)
                .innerJoin(returnStock.prevStock, prevStock)
                .innerJoin(returnStock.nextStock, nextStock)
                .innerJoin(returnStock.store, store).on(
                store.storeId.eq(requestDto.getStoreId())
        )
                .innerJoin(inStock).on(inStock.device.dvcId.eq(returnStock.device.dvcId))
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(maker).on(
                goods.maker.eq(maker.codeSeq)
        )
                .innerJoin(telecom).on(
                goods.networkAttribute.telecom.eq(telecom.codeSeq)
        )
                .where(
                        returnStock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()),
                        betweenReturnStockRegDt(requestDto.getReturnStockRegiDate(), requestDto.getReturnStockRegiDate()),
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        eqNextStockId(requestDto.getNextStockId()),
                        eqReturnStockStatus(requestDto.getReturnStockStatus()),
                        eqStatusStr(requestDto.getStatusStr()),
                        containsBarcode(requestDto.getBarcode()),
                        eqFaultyYn(requestDto.getProductFaultyYn()),
                        eqExtrrStatus(requestDto.getExtrrStatus()),
                        eqColorName(requestDto.getColorName()),
                        eqGoodsId(requestDto.getGoodsId()),
                        eqCapacity(requestDto.getCapacity()),
                        eqTelecom(requestDto.getTelecom()),
                        eqMaker(requestDto.getMaker())
                )
                //.groupBy(returnStock.device)
                //.orderBy(returnStock.regiDateTime.desc(), returnStock.device.dvcId.desc(), inStock.inStockId.desc());
                .orderBy(returnStock.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<ReturnStockResponseDto> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public List<ReturnStockResDto> makeReturnStockInfoFromBarcode(List<String> barcodeDataList, Long storeId) {

        JPQLQuery<ReturnStockResDto> query = getQuerydsl().createQuery();
        QStock prevStock = new QStock("prevStock");
        QStock nextStock = new QStock("nextStock");

        query.select(Projections.fields(
                ReturnStockResDto.class

                , device.dvcId.as("dvcId")
                , device.rawBarcode.as("rawBarcode")

                , Expressions.asNumber(0).as("returnStockAmt")

                , new CaseBuilder()
                        .when(nextStock.isNotNull())
                        .then(nextStock.stockId)
                        .otherwise(prevStock.stockId).as("prevStockId")

                , Projections.fields(
                        DeviceStatusResDto.class
                        , Expressions.asNumber(0L).as("dvcStatusId")
                        , Expressions.asString("N").as("productFaultyYn")
                        , Expressions.asEnum(WmsEnum.DeviceExtrrStatus.T).as("extrrStatus")
                        , Expressions.asString("N").as("productMissYn")
                        //, Expressions.asSimple(null).as("missProduct")
                        , Expressions.asNumber(0).as("ddctAmt")
                        , Expressions.asNumber(0).as("addDdctAmt")
                        , Expressions.asString("N").as("ddctReleaseAmtYn")
                        , Expressions.asEnum(WmsEnum.InStockStatus.NORMAL).as("inStockStatus")
                ).as("returnDeviceStatus")
                )
        )
                .from(device)
                .innerJoin(storeStock)
                .on(
                        device.dvcId.eq(storeStock.device.dvcId)
                                .and(device.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
                                .and(storeStock.store.storeId.eq(storeId))
                )
                .leftJoin(storeStock.prevStock, prevStock)
                .leftJoin(storeStock.nextStock, nextStock)
                .where(
                        device.rawBarcode.in(barcodeDataList).or(
                                device.fullBarcode.in(barcodeDataList).or(
                                        device.serialNo.in(barcodeDataList)
                                )
                        )
                );

        List<ReturnStockResDto> resultList = query.fetch();

        return resultList;
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

    private BooleanExpression eqNextStockId(Long nextStockId) {
        if (nextStockId == null) {
            return null;
        }
        return returnStock.nextStock.stockId.eq(nextStockId);
    }

    private BooleanExpression eqGoodsId(Long goodsId) {
        if (goodsId == null) {
            return null;
        }
        return goods.goodsId.eq(goodsId);
    }

    private BooleanExpression eqReturnStockStatus(WmsEnum.InStockStatus returnStockStatus) {
        if (returnStockStatus == null) {
            return null;
        }
        return deviceStatus.inStockStatus.eq(returnStockStatus);
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

    private BooleanExpression eqStatusStr(WmsEnum.StockStatStr statusStr) {
        if (statusStr == null) {
            return null;
        }

        if (WmsEnum.StockStatStr.I == statusStr) {
            return returnStock.nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode());
        } else {
            return returnStock.nextStock.stockType.ne(TypeEnum.STOCK_TYPE_I.getStatusCode());
        }
    }

    private BooleanExpression betweenReturnStockRegDt(String startDt, String endDt) {
        if (StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)) {
            return null;
        }
        return returnStock.regiDateTime.between(
                RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
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
}
