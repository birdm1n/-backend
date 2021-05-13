package com.daema.wms.repository;

import com.daema.base.domain.QCodeDetail;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.wms.domain.QStock;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomStoreStockRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
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
import static com.daema.wms.domain.QStoreStockCheck.storeStockCheck;

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

        QCodeDetail maker = new QCodeDetail("maker");
        QCodeDetail telecom = new QCodeDetail("telecom");

        query.select(Projections.fields(
                StoreStockResponseDto.class
                , storeStock.storeStockId.as("storeStockId")
                , storeStock.stockType.as("stockType")
                , device.dvcId.as("dvcId")
                , device.fullBarcode.as("fullBarcode")
                , device.inStockAmt.as("inStockAmt")

                , telecom.codeSeq.as("telecom")
                , telecom.codeNm.as("telecomName")
                , maker.codeSeq.as("maker")
                , maker.codeNm.as("makerName")

                , prevStock.stockId.as("prevStockId")
                , prevStock.stockName.as("prevStockName")
                , nextStock.stockId.as("nextStockId")
                , nextStock.stockName.as("nextStockName")
                , new CaseBuilder()
                        .when(nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode()))
                        .then(WmsEnum.StockStatStr.I.getStatusMsg())
                        .otherwise(WmsEnum.StockStatStr.M.getStatusMsg()).as("statusStr")
                , goods.goodsId.as("goodsId")
                , goods.goodsName.as("goodsName")
                , goods.modelName.as("modelName")

                , goodsOption.goodsOptionId.as("goodsOptionId")
                , goodsOption.colorName.as("colorName")
                , goodsOption.commonBarcode.as("commonBarcode")
                , goodsOption.capacity.as("capacity")

                , inStock.regiDateTime.as("inStockRegiDateTime")
                , inStock.inStockMemo.as("inStockMemo")

                , returnStock.returnStockAmt.as("returnStockAmt")
                , returnStock.returnStockMemo.as("returnStockMemo")
                , returnStock.ddctReleaseAmtYn.as("ddctReleaseAmtYn")

                , ExpressionUtils.as(
                        JPAExpressions
                                .select(storeStockCheck.regiDateTime.max())
                                .from(storeStockCheck)
                                .where(storeStock.storeStockId.eq(storeStockCheck.storeStock.storeStockId))
                                .groupBy(storeStockCheck.storeStock.storeStockId),
                        "stockCheckDateTime1")
                , ExpressionUtils.as(
                        JPAExpressions
                                .select(storeStockCheck.regiDateTime.max())
                                .from(storeStockCheck)
                                .where(
                                        storeStock.storeStockId.eq(storeStockCheck.storeStock.storeStockId)
                                                .and(storeStockCheck.regiDateTime
                                                        .lt(JPAExpressions
                                                                .select(storeStockCheck.regiDateTime.max())
                                                                .from(storeStockCheck)
                                                                .where(storeStock.storeStockId
                                                                        .eq(storeStockCheck.storeStock.storeStockId))
                                                                .groupBy(storeStockCheck.storeStock.storeStockId)
                                                        )
                                                )
                                )
                                .groupBy(storeStockCheck.storeStock.storeStockId),
                        "stockCheckDateTime2")
                )


        )

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

                .leftJoin(inStock).on(
                inStock.device.dvcId.eq(storeStock.device.dvcId))
                .leftJoin(returnStock).on(
                returnStock.device.dvcId.eq(storeStock.device.dvcId)
                        .and(returnStock.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
        )

                .where(
                        betweenInStockRegDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        betweenStoreStockCheckDt(requestDto.getInStockRegiDate(), requestDto.getInStockRegiDate()),
                        eqNextStockId(requestDto.getNextStockId()),
                        eqStatusStr(nextStock, requestDto.getStatusStr()),
                        eqInStockStatus(requestDto.getInStockStatus()),
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

    private BooleanExpression eqStatusStr(QStock nextStock, WmsEnum.StockStatStr statusStr) {
        if (statusStr == null) {
            return null;
        }

        if (WmsEnum.StockStatStr.I == statusStr) {
            return nextStock.stockType.eq(TypeEnum.STOCK_TYPE_I.getStatusCode());
        } else {
            return nextStock.stockType.ne(TypeEnum.STOCK_TYPE_I.getStatusCode());
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