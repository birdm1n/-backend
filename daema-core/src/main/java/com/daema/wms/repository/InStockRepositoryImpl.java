package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.QMember;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.commgmt.domain.Goods;
import com.daema.wms.domain.InStock;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockResponseDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import com.daema.wms.repository.custom.CustomInStockRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.daema.commgmt.domain.QGoods.goods;
import static com.daema.commgmt.domain.QGoodsOption.goodsOption;
import static com.daema.commgmt.domain.QStore.store;
import static com.daema.wms.domain.QDevice.device;
import static com.daema.wms.domain.QDeviceStatus.deviceStatus;
import static com.daema.wms.domain.QInStock.inStock;
import static com.daema.wms.domain.QInStockWait.inStockWait;
import static com.daema.wms.domain.QProvider.provider;
import static com.daema.wms.domain.QStock.stock;

public class InStockRepositoryImpl extends QuerydslRepositorySupport implements CustomInStockRepository {

    public InStockRepositoryImpl() {
        super(InStock.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<InStockWaitDto> getWaitInStockList(InStockRequestDto requestDto) {
        JPQLQuery<InStockWaitDto> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                InStockWaitDto.class
//                , inStockWait.waitId.as("waitId")
                , inStockWait.regiDateTime.as("regiDateTime")
//            ,inStockWait.regiUserId.as("regiUserId")
//            ,inStockWait.updDateTime.as("updDateTime")
//            ,inStockWait.updUserId.as("updUserId")
//            ,inStockWait.delYn.as("delYn")
//            ,inStockWait.waitId.as("waitId")
//            ,inStockWait.inStockType.as("inStockType")
//            ,inStockWait.inStockStatus.as("inStockStatus")
//            ,inStockWait.inStockAmt.as("inStockAmt")
//            ,inStockWait.inStockMemo.as("inStockMemo")
//            ,inStockWait.productFaultyYn.as("productFaultyYn")
//            ,inStockWait.extrrStatus.as("extrrStatus")
//            ,inStockWait.productMissYn.as("productMissYn")
//            ,inStockWait.missProduct.as("missProduct")
//            ,inStockWait.ddctAmt.as("ddctAmt")
//            ,inStockWait.ownStoreId.as("ownStoreId")
//            ,inStockWait.holdStoreId.as("holdStoreId")
//            ,inStockWait.goodsOptionId.as("goodsOptionId")
//            ,inStockWait.goodsId.as("goodsId")
        ));

        return query.from(inStockWait)
                .leftJoin(provider).on(inStockWait.provId.eq(provider.provId))
                .leftJoin(store).on(inStockWait.ownStoreId.eq(store.storeId))
                .leftJoin(store).on(inStockWait.ownStoreId.eq(store.storeId))
                .fetch();
    }

    @Override
    public Page<InStockResponseDto> getInStockList(InStockRequestDto requestDto) {
        JPQLQuery<InStockResponseDto> query = getQuerydsl().createQuery();
        QMember regMember = new QMember("regMember");
        QMember updMember = new QMember("updMember");

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
                .orderBy(inStock.regiDateTime.desc())
                .fetch();
        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);
        List<InStockResponseDto> resultList = query.fetch();
        for (InStockResponseDto dto: resultList){
            dto.setInStockStatusMsg(dto.getInStockStatus().getStatusMsg());
            dto.setExtrrStatusMsg(dto.getExtrrStatus().getStatusMsg());
        }
        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public List<Goods> getDeviceList(long storeId, int telecomId, int makerId) {
        JPQLQuery<Goods> query = getQuerydsl().createQuery();
        return query.select(Projections.fields(
                Goods.class
                , goods.goodsId
                , goods.goodsName
        ))
                .distinct()
                .from(device)
                .innerJoin(device.goodsOption, goodsOption).on(
                        device.store.storeId.eq(storeId),
                        device.delYn.eq("N"),
                        goodsOption.delYn.eq("N")
                )
                .innerJoin(goodsOption.goods, goods).on(
                        eqTelecom(telecomId),
                        eqMaker(makerId),
                        goods.delYn.eq("N")
                )
                .orderBy(goods.goodsName.asc())
                .fetch();

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
}