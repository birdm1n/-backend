package com.daema.wms.repository.custom;

import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import com.daema.wms.domain.dto.response.ReturnStockResponseDto;
import org.springframework.data.domain.Page;

public interface CustomReturnStockRepository {
    Page<ReturnStockResponseDto> getSearchPage(ReturnStockRequestDto requestDto);
}
