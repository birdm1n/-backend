package com.daema.core.wms.repository.custom;

import com.daema.core.wms.domain.Provider;
import com.daema.core.wms.dto.request.ProviderRequestDto;
import org.springframework.data.domain.Page;

public interface CustomProviderRepository {
    Page<Provider> getSearchPage(ProviderRequestDto requestDto);
}
