package com.daema.rest.wms.service;

import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import com.daema.wms.repository.BarcodeRepository;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.InStockRepository;
import com.daema.wms.repository.InStockWaitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InStockMgmtService {

    private final InStockRepository inStockRepository;
    private final BarcodeRepository barcodeRepository;
    private final DeviceRepository deviceRepository;
    private final InStockWaitRepository inStockWaitRepository;
    private final AuthenticationUtil authenticationUtil;

    public InStockMgmtService(InStockRepository inStockRepository, BarcodeRepository barcodeRepository, DeviceRepository deviceRepository, InStockWaitRepository inStockWaitRepository, AuthenticationUtil authenticationUtil) {
        this.inStockRepository = inStockRepository;
        this.barcodeRepository = barcodeRepository;
        this.deviceRepository = deviceRepository;
        this.inStockWaitRepository = inStockWaitRepository;
        this.authenticationUtil = authenticationUtil;
    }

    public List<InStockWaitDto> getWaitInStockList(InStockRequestDto requestDto) {
        requestDto.setStoreId(authenticationUtil.getStoreId());
        List<InStockWaitDto> dataList = inStockRepository.getWaitInStockList(requestDto);

        return dataList;
    }

    @Transactional
    public ResponseCodeEnum insertWaitInStock(InStockWaitDto requestDto) {
        Long storeId = authenticationUtil.getStoreId();
        String commonBarcode = CommonUtil.getCmnBarcode(requestDto.getFullBarcode());
        requestDto.setCommonBarcode(commonBarcode);

        InStockWait selectEntity = inStockWaitRepository.findByFullBarcodeAndDelYn(requestDto.getFullBarcode(), StatusEnum.FLAG_N.getStatusMsg());
        if (selectEntity != null) {
            return ResponseCodeEnum.DUPL_DATA;
        }

        //todo inStock => stock에 storeId로 입고된 데이터가 있는지 확인


        InStockWait insertEntity =
                InStockWait.builder()
                        .fullBarcode(requestDto.getFullBarcode())
                        .commonBarcode(requestDto.getCommonBarcode())
                        .inStockStatus(requestDto.getInStockStatus())
                        .inStockAmt(requestDto.getInStockAmt())
                        .inStockMemo(requestDto.getInStockMemo())
                        .provId(requestDto.getProvId())
                        .stockId(requestDto.getStockId())
                        .stockName(requestDto.getStockName())
                        .productFaultyYn(requestDto.getProductFaultyYn())
                        .extrrStatus(requestDto.getExtrrStatus())
                        .productMissYn(requestDto.getProductMissYn())
                        .missProduct(requestDto.getMissProduct())
                        .ddctAmt(requestDto.getDdctAmt())
                        .ownStoreId(storeId)
                        .goodsOptionId(requestDto.getGoodsOptionId())
                        .goodsId(requestDto.getGoodsId())
                        .build();
        inStockWaitRepository.save(insertEntity);
        return ResponseCodeEnum.OK;
    }
}