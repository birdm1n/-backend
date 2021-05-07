package com.daema.wms.repository.custom;

import com.daema.base.domain.CodeDetail;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockResponseDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomInStockRepository {
    List<InStockWaitDto> getWaitInStockList(InStockRequestDto requestDto);

    Page<InStockResponseDto> getSearchPage(InStockRequestDto requestDto);

    List<CodeDetail> getMakerList(int telecom, long stockId);
}
