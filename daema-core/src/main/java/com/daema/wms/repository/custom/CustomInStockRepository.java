package com.daema.wms.repository.custom;

import com.daema.commgmt.domain.Goods;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockResponseDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomInStockRepository {
    List<InStockWaitDto> getWaitInStockList(InStockRequestDto requestDto);

    Page<InStockResponseDto> getInStockList(InStockRequestDto requestDto);

    List<Goods> getDeviceList(long storeId, int telecomId, int makerId);
}
