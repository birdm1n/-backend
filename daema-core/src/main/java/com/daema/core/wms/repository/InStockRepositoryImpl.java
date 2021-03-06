package com.daema.core.wms.repository;

import com.daema.core.base.domain.QCodeDetail;
import com.daema.core.base.domain.QMembers;
import com.daema.core.base.domain.common.RetrieveClauseBuilder;
import com.daema.core.commgmt.domain.Goods;
import com.daema.core.wms.domain.InStock;
import com.daema.core.wms.dto.request.InStockRequestDto;
import com.daema.core.wms.dto.response.InStockResponseDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.repository.custom.CustomInStockRepository;
import com.querydsl.core.QueryResults;
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

import static com.daema.core.commgmt.domain.QGoods.goods;
import static com.daema.core.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.core.commgmt.domain.QStore.store;
import static com.daema.core.wms.domain.QDevice.device;
import static com.daema.core.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.core.wms.domain.QInStock.inStock;
import static com.daema.core.wms.domain.QProvider.provider;
import static com.daema.core.wms.domain.QStock.stock;
import static com.daema.core.wms.domain.QStoreStock.storeStock;

public class InStockRepositoryImpl extends QuerydslRepositorySupport implements CustomInStockRepository {

    public InStockRepositoryImpl() {
        super(InStock.class);
    }

    @PersistenceContext
    private EntityManager em;
    @Override
    public Page<InStockResponseDto> getInStockList(InStockRequestDto requestDto) {
        JPQLQuery<InStockResponseDto> query = getQuerydsl().createQuery();
        QMembers regMember = new QMembers("regMember");
        QMembers updMember = new QMembers("updMember");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");
        query.select(Projections.fields(
                InStockResponseDto.class
                , inStock.inStockId.as("inStockId")
                , deviceStatus.ddctAmt.as("ddctAmt")
                , deviceStatus.addDdctAmt.as("addDdctAmt")
                , deviceStatus.missProduct.as("missProduct")
                , inStock.inStockMemo.as("inStockMemo")
                , telecom.codeSeq.as("telecom")
                , telecom.codeNm.as("telecomName")
                , provider.provId.as("provId")
                , provider.provName.as("provName")
                , stock.stockId.as("stockId")
                , stock.stockName.as("stockName")
                , inStock.statusStr.as("statusStr")
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
                , device.inStockAmt.as("inStockAmt")
                , deviceStatus.inStockStatus.as("inStockStatus")
                , deviceStatus.productFaultyYn.as("productFaultyYn")
                , deviceStatus.productMissYn.as("productMissYn")
                , deviceStatus.extrrStatus.as("extrrStatus")
                , inStock.regiDateTime.as("regiDateTime")
                , regMember.seq.as("regiUserId")
                , regMember.username.as("regiUserName")
                , inStock.updDateTime.as("updDateTime")
                , updMember.seq.as("updUserId")
                , updMember.username.as("updUserName")
                , storeStock.stockType.as("stockType")
        ))
                .from(inStock)
                .innerJoin(inStock.store, store).on(
                    store.storeId.eq(requestDto.getStoreId())
                )
                .innerJoin(inStock.regiUserId, regMember)
                .innerJoin(inStock.updUserId, updMember)
                .innerJoin(inStock.device, device)
                .innerJoin(inStock.inDeviceStatus, deviceStatus)
                .innerJoin(inStock.stock, stock)
                .innerJoin(inStock.provider, provider)
                .innerJoin(device.goodsOption, goodsOption)
                .innerJoin(goodsOption.goods, goods)
                .innerJoin(maker).on(
                    goods.maker.eq(maker.codeSeq)
                )
                .innerJoin(telecom).on(
                    goods.networkAttribute.telecom.eq(telecom.codeSeq)
                )
                .innerJoin(device.storeStock,storeStock)
                .where(
                        betweenInstockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        eqProvId(requestDto.getProvId()),
                        eqStockId(requestDto.getStockId()),
                        eqInStockStatus(requestDto.getInStockStatus()),
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
                .orderBy(inStock.regiDateTime.desc());
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        QueryResults<InStockResponseDto> resultList = query.fetchResults();
        return new PageImpl<>(resultList.getResults(), pageable, resultList.getTotal());
    }


    @Override
    public List<Goods> getDeviceList(long storeId, Long telecomId, Long makerId) {
        JPQLQuery<Goods> query = getQuerydsl().createQuery();
        return query.select(Projections.fields(
                Goods.class
                , goods.goodsId
                , goods.goodsName
        ))
                .distinct()
                .from(device)
                .innerJoin(device.goodsOption, goodsOption).on(
                        device.store.storeId.eq(storeId)
                )
                .innerJoin(goodsOption.goods, goods)
                .where(
                        eqTelecom(telecomId),
                        eqMaker(makerId)
                )
                .orderBy(goods.goodsName.asc())
                .fetch();

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
        return deviceStatus.inStockStatus.eq(inStockStatus);
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