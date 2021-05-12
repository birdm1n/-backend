package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.base.util.CommonUtil;
import com.daema.wms.domain.QDeviceStatus;
import com.daema.wms.domain.QStock;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomStoreStockRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
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
import static com.daema.wms.domain.QReturnStock.returnStock;
import static com.daema.wms.domain.QStoreStock.storeStock;

public class StoreStockRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreStockRepository {

    public StoreStockRepositoryImpl() {
        super(StoreStock.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<StoreStockResponseDto> getStoreStockList(StoreStockRequestDto requestDto) {
        JPQLQuery<StoreStockResponseDto> query = getQuerydsl().createQuery();
        QStock prevStock = new QStock("prevStock");
        QStock nextStock = new QStock("nextStock");
        QDeviceStatus inStockDeviceStatus = new QDeviceStatus("inStockDeviceStatus");
        QDeviceStatus returnStockDeviceStatus = new QDeviceStatus("returnStockDeviceStatus");

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        query.select(Projections.fields(
                StoreStockResponseDto.class
                , storeStock.storeStockId.as("storeStockId")
                , storeStock.stockType.as("stockType")
                , device.dvcId.as("dvcId")
                , device.fullBarcode.as("fullBarcode")
/*

                , deviceStatus.productFaultyYn.as("productFaultyYn")
                , deviceStatus.productMissYn.as("productMissYn")
                , deviceStatus.extrrStatus.as("extrrStatus")
                , deviceStatus.ddctAmt.as("ddctAmt")
                , deviceStatus.addDdctAmt.as("addDdctAmt")
                , deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                , deviceStatus.missProduct.as("missProduct")
*/

                , telecom.codeSeq.as("telecom")
                , telecom.codeNm.as("telecomName")
                , maker.codeSeq.as("maker")
                , maker.codeNm.as("makerName")

                , prevStock.stockId.as("prevStockId")
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockId.as("nextStockId")
                , nextStock.stockName.as("nextStockName")

                , new CaseBuilder()
                        .when(returnStock.nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode())
                                .or(inStock.stock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode())))
                        .then(WmsEnum.StockStatStr.I.getStatusMsg())
                        .otherwise(WmsEnum.StockStatStr.M.getStatusMsg()).as("statusStr")
                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")

                , goodsOption.goodsOptionId.as("goodsOptionId")
                , goodsOption.colorName.as("colorName")
                , goodsOption.commonBarcode.as("commonBarcode")
                , goodsOption.capacity.as("capacity")
// todo 2021 수정
//                , inStock.regiDateTime.as("inStockRegiDateTime")
//                , inStock.inStockAmt.as("inStockAmt")
//                , inStock.inStockStatus.as("inStockStatus")
//                , returnStock.returnStockStatus.as("returnStockStatus")
        ))
                .from(storeStock)
                .innerJoin(storeStock.device, device).on(
                storeStock.device.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
                .and(storeStock.stockYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
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

                .leftJoin(inStock).on(inStock.inStockId.eq(storeStock.stockTypeId))


                .leftJoin(inStock.inDeviceStatus, inStockDeviceStatus)

                .leftJoin(returnStock).on(returnStock.returnStockId.eq(storeStock.stockTypeId))
                .leftJoin(returnStockDeviceStatus)
                .on(returnStock.returnDeviceStatus.eq(returnStockDeviceStatus))

                .where(
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        betweenStoreStockCheckDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        eqNextStockId(requestDto.getNextStockId()),
                        eqStatusStr(requestDto.getStatusStr()),
// todo 2021 수정
//                        eqInStockStatus(requestDto.getInStockStatus()),
                        eqFullBarcode(requestDto.getFullBarcode()),
                        eqFaultyYn(requestDto.getProductFaultyYn()),
                        eqExtrrStatus(requestDto.getExtrrStatus()),
                        eqColorName(requestDto.getColorName()),
                        eqGoodsId(requestDto.getGoodsId()),
                        eqCapacity(requestDto.getCapacity()),
                        eqTelecom(requestDto.getTelecom()),
                        eqMaker(requestDto.getMaker())
                )
                .orderBy(storeStock.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<StoreStockResponseDto> resultList = query.fetch();

        for (StoreStockResponseDto dto : resultList) {
            dto.setReturnStockStatusMsg(dto.getReturnStockStatus().getStatusMsg());
            dto.setInStockStatusMsg(dto.getInStockStatus().getStatusMsg());
            dto.setExtrrStatusMsg(dto.getExtrrStatus().getStatusMsg());
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
        if (nextStockId == null) {
            return null;
        }
        return storeStock.nextStock.stockId.eq(nextStockId);
    }

    private BooleanExpression eqGoodsId(Long goodsId) {
        if (goodsId == null) {
            return null;
        }
        return goods.goodsId.eq(goodsId);
    }
// todo 2021 수정
//    private BooleanExpression eqInStockStatus(WmsEnum.InStockStatus inStockStatus) {
//        if (inStockStatus == null) {
//            return null;
//        }
//        return returnStock.returnStockStatus.eq(inStockStatus)
//                .or(inStock.inStockStatus.eq(inStockStatus));
//    }

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

    private BooleanExpression eqFullBarcode(String fullBarcode) {
        if (StringUtils.isEmpty(fullBarcode)) {
            return null;
        }
        return device.fullBarcode.contains(fullBarcode);
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
            return returnStock.nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode())
                    .or(inStock.stock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()));
        } else {
            return returnStock.nextStock.stockType.ne(TypeEnum.STOCK_TYPE_I.getStatusCode())
                    .and(inStock.stock.stockType.ne(TypeEnum.STOCK_TYPE_I.getStatusCode()));
        }
    }

    private BooleanExpression betweenStoreStockCheckDt(String startDt, String endDt) {
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