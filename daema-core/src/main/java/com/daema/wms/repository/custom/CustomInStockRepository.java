package com.daema.wms.repository.custom;

import com.daema.commgmt.domain.Goods;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomInStockRepository {

    Page<InStockResponseDto> getInStockList(InStockRequestDto requestDto);

    List<Goods> getDeviceList(long storeId, Long telecomId, Long makerId);
}
