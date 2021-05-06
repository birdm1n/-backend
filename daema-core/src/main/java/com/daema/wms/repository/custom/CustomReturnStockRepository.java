package com.daema.wms.repository.custom;

import com.daema.wms.domain.ReturnStock;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import org.springframework.data.domain.Page;

public interface CustomReturnStockRepository {
    Page<ReturnStock> getSearchPage(ReturnStockRequestDto requestDto);
}
