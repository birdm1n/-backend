package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.request.ReturnStockReqDto;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import com.daema.wms.domain.dto.response.ReturnStockResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomReturnStockRepository {
    Page<ReturnStockResponseDto> getSearchPage(ReturnStockRequestDto requestDto);
    List<ReturnStockReqDto> makeReturnStockInfoFromBarcode(List<String> barcodeDataList, Long storeId);
}
