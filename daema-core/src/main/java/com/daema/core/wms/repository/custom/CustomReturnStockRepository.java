package com.daema.core.wms.repository.custom;

import com.daema.core.wms.dto.request.ReturnStockRequestDto;
import com.daema.core.wms.dto.response.ReturnStockResDto;
import com.daema.core.wms.dto.response.ReturnStockResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomReturnStockRepository {
    Page<ReturnStockResponseDto> getSearchPage(ReturnStockRequestDto requestDto);
    List<ReturnStockResDto> makeReturnStockInfoFromBarcode(List<String> barcodeDataList, Long storeId);
}
