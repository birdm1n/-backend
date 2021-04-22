package com.daema.wms.repository.custom;

import com.daema.wms.domain.Provider;
import com.daema.wms.domain.dto.request.ProviderRequestDto;
import org.springframework.data.domain.Page;

public interface CustomProviderRepository {
    Page<Provider> getSearchPage(ProviderRequestDto requestDto);
}
