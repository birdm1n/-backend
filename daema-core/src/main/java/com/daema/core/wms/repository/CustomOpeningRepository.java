package com.daema.core.wms.repository;

import com.daema.core.wms.dto.request.OpeningCurrentRequestDto;
import com.daema.core.wms.dto.response.OpeningCurrentResponseDto;
import org.springframework.data.domain.Page;

public interface CustomOpeningRepository {

    Page<OpeningCurrentResponseDto> getOpeningCurrentList(OpeningCurrentRequestDto openingCurrentRequestDto);

}
