package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.QMember;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomStoreStockRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.daema.commgmt.domain.QGoods.goods;
import static com.daema.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.commgmt.domain.QStore.store;
import static com.daema.wms.domain.QDevice.device;
import static com.daema.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.wms.domain.QInStock.inStock;
import static com.daema.wms.domain.QProvider.provider;
import static com.daema.wms.domain.QStock.stock;

public class StoreStockRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreStockRepository {

    public StoreStockRepositoryImpl() {
        super(StoreStock.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<StoreStockResponseDto> getStoreStockList(StoreStockRequestDto requestDto) {
        JPQLQuery<StoreStockResponseDto> query = getQuerydsl().createQuery();
        QMember regMember = new QMember("regMember");
        QMember updMember = new QMember("updMember");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");
        query.select(Projections.fields(
                StoreStockResponseDto.class
                , inStock.inStockId.as("inStockId")
                , deviceStatus.ddctAmt.as("ddctAmt")
                , deviceStatus.addDdctAmt.as("addDdctAmt")
                , deviceStatus.missProduct.as("missProduct")
                , inStock.inStockMemo.as("inStockMemo")
                , telecom.codeSeq.as("telecom")
                , telecom.codeNm.as("telecomName")
                , provider.provId.as("provId")
                , stock.stockId.as("stockId")
                , stock.stockName.as("stockName")
                , inStock.statusStr.as("statusStr")
                , maker.codeSeq.as("maker")
                , maker.codeNm.as("makerName")
                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")
                , goods.capacity.as("capacity")
                , goodsOption.goodsOptionId.as("goodsOptionId")
                , goodsOption.colorName.as("colorName")
                , goodsOption.commonBarcode.as("commonBarcode")
                , device.fullBarcode.as("fullBarcode")
                , inStock.inStockAmt.as("inStockAmt")
                , inStock.inStockStatus.as("inStockStatus")
                , deviceStatus.productFaultyYn.as("productFaultyYn")
                , deviceStatus.productMissYn.as("productMissYn")
                , deviceStatus.extrrStatus.as("extrrStatus")
                , inStock.regiDateTime.as("regiDateTime")
                , regMember.seq.as("regiUserId")
                , regMember.username.as("regiUserName")
                , inStock.updDateTime.as("updDateTime")
                , updMember.seq.as("updUserId")
                , updMember.username.as("updUserName")
        ))
                .from(inStock)
                .innerJoin(inStock.regiUserId, regMember)
                .innerJoin(inStock.regiUserId, updMember)
                .innerJoin(inStock.device, device)
                .innerJoin(inStock.inDeviceStatus, deviceStatus)
                .innerJoin(inStock.stock, stock)
                .innerJoin(inStock.store, store).on(
                    store.storeId.eq(requestDto.getStoreId())
                )
                .innerJoin(inStock.provider, provider)
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(maker).on(
                    goods.maker.eq(maker.codeSeq)
                )
                .innerJoin(telecom).on(
                    goods.networkAttribute.telecom.eq(telecom.codeSeq)
                )
                /*
                .where(
                        betweenInstockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        eqProvId(requestDto.getProvId()),
                        eqStockId(requestDto.getStockId()),
                        eqInStockStatus(requestDto.getInStockStatus()),
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
                */
                .orderBy(inStock.regiDateTime.desc())
                .fetch();
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<StoreStockResponseDto> resultList = query.fetch();

        for (StoreStockResponseDto dto: resultList){
            dto.setInStockStatusMsg(dto.getInStockStatus().getStatusMsg());
            dto.setExtrrStatusMsg(dto.getExtrrStatus().getStatusMsg());
        }

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression eqTelecom(int telecom) {
        if (telecom <= 0) {
            return null;
        }
        return goods.networkAttribute.telecom.eq(telecom);
    }

    private BooleanExpression eqMaker(int maker) {
        if (maker <= 0) {
            return null;
        }
        return goods.maker.eq(maker);
    }
    private BooleanExpression eqProvId(Long provId) {
        if(provId == null){
            return null;
        }
        return inStock.provider.provId.eq(provId);

    }
    private BooleanExpression eqStockId(Long stockId) {
        if(stockId == null){
            return null;
        }
        return inStock.stock.stockId.eq(stockId);
    }
    private BooleanExpression eqGoodsId(Long goodsId) {
        if(goodsId == null){
            return null;
        }
        return goods.goodsId.eq(goodsId);
    }

    private BooleanExpression eqInStockStatus(WmsEnum.InStockStatus inStockStatus) {
        if (inStockStatus == null){
            return null;
        }
        return inStock.inStockStatus.eq(inStockStatus);
    }
    private BooleanExpression eqCapacity(String capacity) {
        if(StringUtils.isEmpty(capacity)){
            return null;
        }
        return goods.capacity.eq(capacity);
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
        return device.fullBarcode.eq(fullBarcode);
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
        return inStock.statusStr.eq(statusStr);
    }


    private BooleanExpression betweenInstockRegDt(String startDt, String endDt){
        if(StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)){
            return null;
        }
        return inStock.regiDateTime.between(
                RetrieveClauseBuilder.stringToLocalDateTime(startDt, "s"),
                RetrieveClauseBuilder.stringToLocalDateTime(endDt, "e")
        );
    }

}