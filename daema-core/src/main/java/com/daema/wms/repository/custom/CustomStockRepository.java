package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.SelectStockDto;

import java.util.HashMap;
import java.util.List;

public interface CustomStockRepository {
    HashMap<String, List> getStockAndDeviceList(StockRequestDto requestDto);

    List<SelectStockDto> selectStockList(long storeId, Integer telecom);

    SelectStockDto getStock(Long storeId, Integer telecom, Long stockId);

    List<SelectStockDto> innerStockList(long storeId);

    /* 상점에 기기보유 여부 확인 */
    Long stockHasDevice(Long stockId, Long regiStoreId);
}
