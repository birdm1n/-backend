package com.daema.core.wms.repository.custom;

import com.daema.core.commgmt.domain.Goods;
import com.daema.core.wms.dto.request.InStockRequestDto;
import com.daema.core.wms.dto.response.InStockResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomInStockRepository {

    Page<InStockResponseDto> getInStockList(InStockRequestDto requestDto);

    List<Goods> getDeviceList(long storeId, Long telecomId, Long makerId);
}
