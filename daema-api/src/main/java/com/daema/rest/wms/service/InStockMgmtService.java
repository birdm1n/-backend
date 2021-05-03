package com.daema.rest.wms.service;

import com.daema.commgmt.domain.PubNoti;
import com.daema.commgmt.domain.dto.response.GoodsMatchRespDto;
import com.daema.commgmt.repository.GoodsRepository;
import com.daema.commgmt.repository.PubNotiRepository;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.wms.domain.InStock;
import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.Provider;
import com.daema.wms.domain.dto.request.InStockWaitInsertReqDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import com.daema.wms.domain.dto.response.InStockWaitGroupDto;
import com.daema.wms.domain.dto.response.InStockWaitResponseDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import com.daema.wms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InStockMgmtService {

    private final InStockRepository inStockRepository;
    private final BarcodeRepository barcodeRepository;
    private final DeviceRepository deviceRepository;
    private final PubNotiRepository pubNotiRepository;
    private final StockRepository stockRepository;
    private final GoodsRepository goodsRepository;
    private final InStockWaitRepository inStockWaitRepository;
    private final ProviderRepository providerRepository;
    private final AuthenticationUtil authenticationUtil;

    public InStockMgmtService(InStockRepository inStockRepository, BarcodeRepository barcodeRepository, DeviceRepository deviceRepository, PubNotiRepository pubNotiRepository, StockRepository stockRepository, GoodsRepository goodsRepository, InStockWaitRepository inStockWaitRepository, ProviderRepository providerRepository, AuthenticationUtil authenticationUtil) {
        this.inStockRepository = inStockRepository;
        this.barcodeRepository = barcodeRepository;
        this.deviceRepository = deviceRepository;
        this.pubNotiRepository = pubNotiRepository;
        this.stockRepository = stockRepository;
        this.goodsRepository = goodsRepository;
        this.inStockWaitRepository = inStockWaitRepository;
        this.providerRepository = providerRepository;
        this.authenticationUtil = authenticationUtil;
    }

    @Transactional(readOnly = true)
    public InStockWaitResponseDto getWaitInStockList(InStock.StockStatus inStockStatus) {
        long storeId = authenticationUtil.getStoreId();
        List<InStockWait> entityList = inStockWaitRepository.findByOwnStoreIdOrHoldStoreIdAndDelYnAndInStockStatusOrderByRegiDateTimeDesc(storeId, storeId, StatusEnum.FLAG_N.getStatusMsg(), inStockStatus);

        InStockWaitResponseDto responseDto = new InStockWaitResponseDto();

        List<InStockWaitDto> InStockWaitDtoList = entityList.stream()
                .map(InStockWaitDto::from)
                .collect(Collectors.toList());
        responseDto.setInStockWaitDtoList(InStockWaitDtoList);

        List<InStockWaitGroupDto> inStockWaitGroupDtoList = inStockWaitRepository.groupInStockWaitList(storeId, inStockStatus);
        responseDto.setInStockWaitGroupDtoList(inStockWaitGroupDtoList);

//        Map<String, List<InStockWaitGroupDto>> test = entityList.stream()
//                .map(InStockWaitGroupDto::from)
//                .collect(
//                        groupingBy(
//                                InStockWaitGroupDto::getTelecomName
//                        )
//                );
        return responseDto;
    }

    @Transactional
    public ResponseCodeEnum insertWaitInStock(InStockWaitInsertReqDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String commonBarcode = CommonUtil.getCmnBarcode(requestDto.getFullBarcode());

        // 중복 입력 확인용
        InStockWait selectEntity = inStockWaitRepository.findByFullBarcodeAndDelYn(requestDto.getFullBarcode(), StatusEnum.FLAG_N.getStatusMsg());
        if (selectEntity != null) {
            return ResponseCodeEnum.DUPL_DATA;
        }

        // 공급처 정보
        Provider provider = providerRepository.findByProvIdAndDelYn(requestDto.getProvId(), StatusEnum.FLAG_N.getStatusMsg());
        if (provider == null) {
            return ResponseCodeEnum.NO_PROV;
        }

        // 보유처 정보
        SelectStockDto stockDto = stockRepository.getStock(storeId, requestDto.getTelecom(), requestDto.getStockId());
        if (stockDto == null) {
            return ResponseCodeEnum.NO_STOCK;
        }
        String statusStr = storeId != stockDto.getStoreId() ? "이동재고" : "매장재고";


        //todo inStock => stock에 storeId로 입고된 데이터가 있는지 확인

        // 상품정보
        GoodsMatchRespDto goodsMatchRespDto = goodsRepository.goodsMatchBarcode(commonBarcode);
        if (goodsMatchRespDto != null) {
            return ResponseCodeEnum.NODATA;
        }
        if (requestDto.getTelecom() != goodsMatchRespDto.getTelecom()) {
            return ResponseCodeEnum.NOT_MATCH_TELECOM;
        }
        // todo 입고단가가 존재하지 않는경우 처리 / release_amt 0원처리?
        int inStockAmt = 0;
        PubNoti pubNoti = pubNotiRepository.findTopByGoodsIdOrderByRegiDateTimeDesc(goodsMatchRespDto.getGoodsId());
        if (pubNoti != null) {
            inStockAmt = pubNoti.getReleaseAmt();
        }

        InStockWait insertEntity =
                InStockWait.builder()
                        .telecom(goodsMatchRespDto.getTelecom())
                        .telecomName(goodsMatchRespDto.getTelecomName())
                        .provId(provider.getProvId())
                        .stockId(stockDto.getStockId())
                        .stockName(stockDto.getStockName())
                        .statusStr(statusStr)
                        .maker(goodsMatchRespDto.getMaker())
                        .makerName(goodsMatchRespDto.getMakerName())
                        .goodsId(goodsMatchRespDto.getGoodsId())
                        .goodsName(goodsMatchRespDto.getGoodsName())
                        .modelName(goodsMatchRespDto.getModelName())
                        .capacity(goodsMatchRespDto.getCapacity())
                        .goodsOptionId(goodsMatchRespDto.getGoodsOptionId())
                        .colorName(goodsMatchRespDto.getColorName())
                        .fullBarcode(requestDto.getFullBarcode())
                        .commonBarcode(commonBarcode)
                        .inStockAmt(inStockAmt)
                        .inStockStatus(requestDto.getInStockStatus())
                        .productFaultyYn(requestDto.getProductFaultyYn())
                        .extrrStatus(requestDto.getExtrrStatus())
                        .inStockMemo(requestDto.getInStockMemo())
                        .productMissYn(requestDto.getProductMissYn())
                        .missProduct(requestDto.getMissProduct())
                        .ddctAmt(requestDto.getDdctAmt())
                        .addDdctAmt(requestDto.getAddDdctAmt())
                        .ownStoreId(storeId)
                        .holdStoreId(stockDto.getStoreId())
                        .build();
        inStockWaitRepository.save(insertEntity);
        return ResponseCodeEnum.OK;
    }
}