package com.daema.wms.repository;

import com.daema.wms.domain.dto.request.OpeningCurrentRequestDto;
import com.daema.wms.domain.dto.response.OpeningCurrentResponseDto;
import org.springframework.data.domain.Page;

public interface CustomOpeningRepository {

    Page<OpeningCurrentResponseDto> getOpeningCurrentList(OpeningCurrentRequestDto openingCurrentRequestDto);

}
