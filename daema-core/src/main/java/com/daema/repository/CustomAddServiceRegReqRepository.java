package com.daema.repository;

import com.daema.domain.AddServiceRegReq;
import com.daema.domain.dto.common.SearchParamDto;
import org.springframework.data.domain.Page;

public interface CustomAddServiceRegReqRepository {
	
	Page<AddServiceRegReq> getSearchPage(SearchParamDto requestDto, boolean isAdmin);
}
