package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.QMember;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.base.enums.TypeEnum;
import com.daema.base.util.CommonUtil;
import com.daema.wms.domain.QStock;
import com.daema.wms.domain.ReturnStock;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import com.daema.wms.domain.dto.response.ReturnStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomReturnStockRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
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
import static com.daema.wms.domain.QDevice.device;
import static com.daema.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.wms.domain.QInStock.inStock;
import static com.daema.wms.domain.QReturnStock.returnStock;

public class ReturnStockRepositoryImpl extends QuerydslRepositorySupport implements CustomReturnStockRepository {

    public ReturnStockRepositoryImpl() {
        super(ReturnStock.class);
    }

    @Override
    public Page<ReturnStockResponseDto> getSearchPage(ReturnStockRequestDto requestDto) {

        JPQLQuery<ReturnStockResponseDto> query = getQuerydsl().createQuery();
        QMember regMember = new QMember("regMember");
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
                , device.fullBarcode.as("fullBarcode")
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
                .innerJoin(returnStock.device.inStocks, inStock)
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(maker).on(
                goods.maker.eq(maker.codeSeq)
        )
                .innerJoin(telecom).on(
                goods.networkAttribute.telecom.eq(telecom.codeSeq)
        )
                .where(
                        betweenReturnStockRegDt(requestDto.getReturnStockRegiDate(), requestDto.getReturnStockRegiDate()),
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        eqNextStockId(requestDto.getNextStockId()),
                        eqReturnStockStatus(requestDto.getReturnStockStatus()),
                        eqStatusStr(requestDto.getStatusStr()),
                        eqFullBarcode(requestDto.getFullBarcode()),
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

        for (ReturnStockResponseDto dto: resultList){
            dto.setReturnStockStatusMsg(dto.getReturnStockStatus().getStatusMsg());
            dto.setExtrrStatusMsg(dto.getExtrrStatus().getStatusMsg());
            dto.setDiffReturnStockRegiDate(CommonUtil.diffDaysLocalDate(dto.getRegiDateTime().toLocalDate()));
            dto.setDiffInStockRegiDate(CommonUtil.diffDaysLocalDate(dto.getInStockRegiDateTime().toLocalDate()));
        }
        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
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
    private BooleanExpression eqNextStockId(Long nextStockId) {
        if(nextStockId == null){
            return null;
        }
        return returnStock.nextStock.stockId.eq(nextStockId);
    }
    private BooleanExpression eqGoodsId(Long goodsId) {
        if(goodsId == null){
            return null;
        }
        return goods.goodsId.eq(goodsId);
    }
    private BooleanExpression eqReturnStockStatus(WmsEnum.InStockStatus returnStockStatus) {
        if (returnStockStatus == null){
            return null;
        }
        return deviceStatus.inStockStatus.eq(returnStockStatus);
    }
    private BooleanExpression eqCapacity(String capacity) {
        if(StringUtils.isEmpty(capacity)){
            return null;
        }
        return goodsOption.capacity.eq(capacity);
    }
    private BooleanExpression eqColorName(String colorName) {
        if(StringUtils.isEmpty(colorName)){
            return null;
        }
        return goodsOption.colorName.eq(colorName);
    }

    private BooleanExpression eqFullBarcode(String fullBarcode) {
        if(StringUtils.isEmpty(fullBarcode)){
            return null;
        }
        return device.fullBarcode.contains(fullBarcode);
    }

    private BooleanExpression eqExtrrStatus(WmsEnum.DeviceExtrrStatus extrrStatus) {
        if(extrrStatus == null){
            return null;
        }
        return deviceStatus.extrrStatus.eq(extrrStatus);
    }

    private BooleanExpression eqFaultyYn(String productFaultyYn) {
        if(StringUtils.isEmpty(productFaultyYn)){
            return null;
        }
        return deviceStatus.productFaultyYn.eq(productFaultyYn);
    }

    private BooleanExpression eqStatusStr(WmsEnum.StockStatStr statusStr) {
        if(statusStr == null){
            return null;
        }

        if(WmsEnum.StockStatStr.I == statusStr){
            return returnStock.nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode());
        }else{
            return returnStock.nextStock.stockType.ne(TypeEnum.STOCK_TYPE_I.getStatusCode());
        }
    }

    private BooleanExpression betweenReturnStockRegDt(String startDt, String endDt){
        if(StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)){
            return null;
        }
        return returnStock.regiDateTime.between(
                RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
        );
    }

    private BooleanExpression betweenInStockRegDt(String startDt, String endDt){
        if(StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)){
            return null;
        }
        return inStock.regiDateTime.between(
                RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
        );
    }
}
