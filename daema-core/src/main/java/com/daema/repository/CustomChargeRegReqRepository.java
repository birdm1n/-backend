package com.daema.repository;

import com.daema.domain.ChargeRegReq;
import com.daema.domain.dto.common.SearchParamDto;
import org.springframework.data.domain.Page;

public interface CustomChargeRegReqRepository {
	
	Page<ChargeRegReq> getSearchPage(SearchParamDto requestDto, boolean isAdmin);
}
