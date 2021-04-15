package com.daema.repository;

import com.daema.domain.AddService;
import com.daema.domain.dto.common.SearchParamDto;
import org.springframework.data.domain.Page;

public interface CustomAddServiceRepository {
	
	Page<AddService> getSearchPage(SearchParamDto requestDto, boolean isAdmin);
}
