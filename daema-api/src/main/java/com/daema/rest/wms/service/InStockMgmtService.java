package com.daema.rest.wms.service;

import com.daema.commgmt.domain.PubNoti;
import com.daema.commgmt.domain.dto.response.GoodsMatchRespDto;
import com.daema.commgmt.repository.GoodsRepository;
import com.daema.commgmt.repository.PubNotiRepository;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.Provider;
import com.daema.wms.domain.Stock;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import com.daema.wms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<InStockWaitDto> getWaitInStockList(InStockRequestDto requestDto) {
        requestDto.setStoreId(authenticationUtil.getStoreId());
        List<InStockWaitDto> dataList = inStockRepository.getWaitInStockList(requestDto);

        return dataList;
    }

    @Transactional
    public ResponseCodeEnum insertWaitInStock(InStockWaitDto requestDto) {
        long storeId = authenticationUtil.getStoreId();
        String commonBarcode = CommonUtil.getCmnBarcode(requestDto.getFullBarcode());

        // 중복 입력 확인용
        InStockWait selectEntity = inStockWaitRepository.findByFullBarcodeAndDelYn(requestDto.getFullBarcode(), StatusEnum.FLAG_N.getStatusMsg());
        if (selectEntity != null) {
            return ResponseCodeEnum.DUPL_DATA;
        }
        
        // 공급처 정보

        Provider provider = providerRepository.findByProvIdAndDelYn(requestDto.getProvId(), StatusEnum.FLAG_N.getStatusMsg());
        if(provider == null){
            return ResponseCodeEnum.NO_PROV;
        }

        // 보유처 정보
        Stock stockEntity = stockRepository.findByStockIdAndDelYn(requestDto.getStockId(), StatusEnum.FLAG_N.getStatusMsg());
        if (stockEntity == null) {
            return ResponseCodeEnum.NO_STOCK;
        }

        //todo inStock => stock에 storeId로 입고된 데이터가 있는지 확인
        
        // 상품정보
        GoodsMatchRespDto goodsMatchRespDto = goodsRepository.goodsMatchBarcode(commonBarcode);
        if (goodsMatchRespDto != null) {
            return ResponseCodeEnum.NODATA;
        }
        if(requestDto.getTelecom() != goodsMatchRespDto.getTelecom()){
            return ResponseCodeEnum.NOT_MATCH_TELECOM;
        }
        // todo 입고단가가 존재하지 않는경우 처리 / release_amt 0원처리?
        int inStockAmt = 0;
        PubNoti pubNoti = pubNotiRepository.findTopByGoodsIdOrderByRegiDateTimeDesc(goodsMatchRespDto.getGoodsId());
        if(pubNoti != null){
            inStockAmt = pubNoti.getReleaseAmt();
        }



        InStockWait insertEntity =
                InStockWait.builder()
                        .provId(requestDto.getProvId())
                        .stockId(requestDto.getStockId())
                        .stockName(requestDto.getStockName())
                        .fullBarcode(requestDto.getFullBarcode())
                        .commonBarcode(commonBarcode)
                        .inStockStatus(requestDto.getInStockStatus()) // 정상, 개봉
                        .inStockMemo(requestDto.getInStockMemo())
                        .productFaultyYn(requestDto.getProductFaultyYn())
                        .extrrStatus(requestDto.getExtrrStatus())
                        .productMissYn(requestDto.getProductMissYn())
                        .missProduct(requestDto.getMissProduct())
                        .ddctAmt(requestDto.getDdctAmt())
                        .ownStoreId(storeId)
                        .holdStoreId(stockEntity.getRegiStoreId())

                        .inStockAmt(inStockAmt)
                        .goodsOptionId(goodsMatchRespDto.getGoodsOptionId())
                        .goodsId(goodsMatchRespDto.getGoodsId())
                        .build();
        inStockWaitRepository.save(insertEntity);
        return ResponseCodeEnum.OK;
    }
}