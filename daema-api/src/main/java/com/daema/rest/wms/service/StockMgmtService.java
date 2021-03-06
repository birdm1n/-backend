package com.daema.rest.wms.service;

import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.commgmt.repository.StoreRepository;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.core.wms.domain.Device;
import com.daema.core.wms.domain.Stock;
import com.daema.core.wms.domain.StockTmp;
import com.daema.core.wms.domain.enums.WmsEnum;
import com.daema.core.wms.dto.StockMgmtDto;
import com.daema.core.wms.dto.request.SellMoveInsertReqDto;
import com.daema.core.wms.dto.request.StockMoveInsertReqDto;
import com.daema.core.wms.dto.request.StockRequestDto;
import com.daema.core.wms.dto.response.SearchMatchResponseDto;
import com.daema.core.wms.dto.response.SelectStockDto;
import com.daema.core.wms.dto.response.StockListDto;
import com.daema.core.wms.dto.response.StockMgmtResponseDto;
import com.daema.core.wms.repository.StockRepository;
import com.daema.core.wms.repository.StockTmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockMgmtService {

    private final DeviceMgmtService deviceMgmtService;

    private final StockRepository stockRepository;

    private final StoreRepository storeRepository;

    private final MoveStockMgmtService moveStockMgmtService;

    private final AuthenticationUtil authenticationUtil;

    private final StockTmpRepository stockTmpRepository;

    public StockMgmtResponseDto getStockList(StockRequestDto requestDto) {

        requestDto.setStoreId(authenticationUtil.getStoreId());

        StockMgmtResponseDto stockMgmtResponseDto = new StockMgmtResponseDto();

        HashMap<String, List> stockDeviceListMap = stockRepository.getStockAndDeviceList(requestDto);

        List<Stock> stockList = stockDeviceListMap.get("stockList");

        stockMgmtResponseDto.setStoreName(storeRepository.findById(requestDto.getStoreId()).orElseGet(Store::new).getStoreName());
        stockMgmtResponseDto.setStockList(stockList.stream()
                .map(StockListDto::new).collect(Collectors.toList()));
        stockMgmtResponseDto.setStockDeviceList(stockDeviceListMap.get("stockDeviceList"));

        //TODO dvcCnt ??? ???????????? ?????? ?????????. ?????? ????????? ??????

        return stockMgmtResponseDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertStock(StockMgmtDto stockMgmtDto) {

        //?????? ?????? ????????? ?????? ????????? ???????????? storeId ??????
        if (TypeEnum.STOCK_TYPE_I.getStatusCode().equals(stockMgmtDto.getStockType())) {
            //?????????(?????????) ?????? ?????????, ?????? ????????? ???????????? ????????? ?????? ??????????????? ???????????? ??????
            if (!authenticationUtil.hasRole("ROLE_ANONYMOUS")
                    || authenticationUtil.getStoreId() > 0) {
                stockMgmtDto.setStoreId(authenticationUtil.getStoreId());
            }
        }

        if (stockMgmtDto.getRegiStoreId() == 0) {
            stockMgmtDto.setRegiStoreId(authenticationUtil.getStoreId());
        }

        if (stockMgmtDto.getRegiUserId() == 0) {
            stockMgmtDto.setRegiUserId(authenticationUtil.getMemberSeq());
            stockMgmtDto.setUpdUserId(authenticationUtil.getMemberSeq());
        }

        if (stockMgmtDto.getParentStockId() > 0) {
            stockMgmtDto.setParentStock(Stock.builder().stockId(stockMgmtDto.getParentStockId()).build());
        }

        stockRepository.save(
                Stock.builder()
                        .stockId(stockMgmtDto.getStockId())
                        .stockName(stockMgmtDto.getStockName())
                        .parentStock(stockMgmtDto.getParentStock())
                        .storeId(stockMgmtDto.getStoreId())
                        .stockType(stockMgmtDto.getStockType())
                        .regiStoreId(stockMgmtDto.getRegiStoreId())
                        .chargerName(stockMgmtDto.getChargerName())
                        .chargerPhone(
                                stockMgmtDto.getChargerPhone1()
                                        .concat(stockMgmtDto.getChargerPhone2())
                                        .concat(stockMgmtDto.getChargerPhone3())
                        )
                        .chargerPhone1(stockMgmtDto.getChargerPhone1())
                        .chargerPhone2(stockMgmtDto.getChargerPhone2())
                        .chargerPhone3(stockMgmtDto.getChargerPhone3())
                        .delYn(StatusEnum.FLAG_N.getStatusMsg())
                        .regiUserId(stockMgmtDto.getRegiUserId())
                        .regiDateTime(LocalDateTime.now())
                        .updUserId(stockMgmtDto.getUpdUserId())
                        .updDateTime(LocalDateTime.now())
                        .build()
        );
    }

    @Transactional
    public void updateStock(StockMgmtDto stockMgmtDto) {

        Stock stock = stockRepository.findById(stockMgmtDto.getStockId()).orElse(null);
        long storeId = authenticationUtil.getStoreId();

        if (stock != null
                && stock.getRegiStoreId() == storeId) {
            stock.setStockName(stockMgmtDto.getStockName());
            stock.setChargerName(stockMgmtDto.getChargerName());
            stock.setChargerPhone(
                    stockMgmtDto.getChargerPhone1()
                            .concat(stockMgmtDto.getChargerPhone2())
                            .concat(stockMgmtDto.getChargerPhone3())
            );
            stock.setChargerPhone1(stockMgmtDto.getChargerPhone1());
            stock.setChargerPhone2(stockMgmtDto.getChargerPhone2());
            stock.setChargerPhone3(stockMgmtDto.getChargerPhone3());
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional(readOnly = true)
    public List<SelectStockDto> selectStockList(Long telecom) {
        long storeId = authenticationUtil.getStoreId();
        return stockRepository.selectStockList(storeId, telecom);
    }

    @Transactional(readOnly = true)
    public List<SelectStockDto> innerStockList() {
        long storeId = authenticationUtil.getStoreId();
        return stockRepository.innerStockList(storeId);
    }

    @Transactional(readOnly = true)
    public SearchMatchResponseDto getDeviceStock(String selDvcId) {
        long storeId = authenticationUtil.getStoreId();
        Store store = Store.builder().storeId(storeId).build();
        //device ???????????? ????????? ????????? ????????? ??????
        Device deviceEntity = deviceMgmtService.retrieveDeviceFromSelDvcId(selDvcId);

        if (deviceEntity == null) {
            return null;
        }

        Stock stockEntity = deviceEntity.getStoreStock().getNextStock(); //?????? ?????????
        SearchMatchResponseDto responseDto = null;
        if (stockEntity != null) {
            responseDto = SearchMatchResponseDto
                    .builder()
                    .stockId(stockEntity.getStockId())
                    .stockName(stockEntity.getStockName())
                    .build();
        }
        return responseDto;
    }


    @Transactional
    public void deleteStock(Long stockId) {

        Long hasDeviceCnt = stockRepository.stockHasDevice(stockId, authenticationUtil.getStoreId());

        if (hasDeviceCnt <= 0) {

            Stock stock = stockRepository.findById(stockId).orElse(null);
            long storeId = authenticationUtil.getStoreId();

            if (stock != null
                    && stock.getRegiStoreId() == storeId) {

                stock.updateDelYn(stock, StatusEnum.FLAG_Y.getStatusMsg(), authenticationUtil.getMemberSeq());

            } else {
                throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }

        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_EMPTY.name());
        }
    }

    @Transactional
    public HashMap<String, List<String>> migrationTelkitMoveStockData() {

        HashMap<String, List<String>> failMap = new HashMap<>();

        List<String> moveStockCnt = new ArrayList<>();
        List<String> sellStockCnt = new ArrayList<>();
        List<String> moveStockMsges = new ArrayList<>();
        List<String> sellStockMsges = new ArrayList<>();

        //????????????
        Long[] longs = {1L, 2L};
        List<StockTmp> moveAndSellStockList = stockTmpRepository.getTelkitStockList(longs);

        long dvcId = 0L;
        ResponseCodeEnum resultCode =  ResponseCodeEnum.OK;

        for(StockTmp stockTmp : moveAndSellStockList){

            StockMoveInsertReqDto requestDto = new StockMoveInsertReqDto();
            requestDto.setDeliveryType(WmsEnum.DeliveryType.UNKNOWN);
            requestDto.setNextStockId(stockTmp.getNowStockId());
            requestDto.setDeliveryMemo(stockTmp.getMemo());
            requestDto.setSelDvcId(String.valueOf(stockTmp.getSelDvcId()));
            dvcId = stockTmp.getSelDvcId();

            resultCode =  ResponseCodeEnum.OK;

            try {

                //StockMoveInsertReqDto
                resultCode = moveStockMgmtService.insertStockMove(requestDto);
            }catch (Exception e){
                e.getMessage();
                moveStockCnt.add(String.valueOf(dvcId));
                sellStockMsges.add(e.getMessage());
            }

            if(resultCode != ResponseCodeEnum.OK){
                moveStockCnt.add(String.valueOf(dvcId));
                moveStockMsges.add(resultCode.getResultMsg());
            }
        }









        //??? INIT;
        dvcId = 0;
        resultCode =  ResponseCodeEnum.OK;

        //????????????
        Long[] sellLongs = {2L};
        List<StockTmp> sellMoveList = stockTmpRepository.getTelkitStockList(sellLongs);

        for(StockTmp stockTmp : sellMoveList){
            SellMoveInsertReqDto requestDto = new SellMoveInsertReqDto();
            requestDto.setDeliveryType(WmsEnum.DeliveryType.UNKNOWN);
            requestDto.setDeliveryMemo(stockTmp.getMemo());
            requestDto.setCusName(stockTmp.getMemo());
            requestDto.setCusPhone1("");
            requestDto.setCusPhone2("");
            requestDto.setCusPhone3("");
            requestDto.setSelDvcId(String.valueOf(stockTmp.getSelDvcId()));

            dvcId = stockTmp.getSelDvcId();

            resultCode =  ResponseCodeEnum.OK;

            try {
                //SellMoveInsertReqDto
                resultCode = moveStockMgmtService.insertSellMove(requestDto);
            }catch (Exception e){
                e.getMessage();
                sellStockCnt.add(String.valueOf(dvcId));
                sellStockMsges.add(e.getMessage());
            }
            if(resultCode != ResponseCodeEnum.OK){
                sellStockCnt.add(String.valueOf(dvcId));
                sellStockMsges.add(resultCode.getResultMsg());
            }
        }

        failMap.put("???????????? ?????? ?????????", moveStockCnt);
        failMap.put("???????????? ?????? ?????????", sellStockCnt);
        failMap.put("???????????? ?????? ?????????", moveStockMsges);
        failMap.put("???????????? ?????? ?????????", sellStockMsges);

        return failMap;
    }
}



































