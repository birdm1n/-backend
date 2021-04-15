package com.daema.repository;

import com.daema.domain.GoodsRegReq;
import com.daema.domain.dto.common.SearchParamDto;
import org.springframework.data.domain.Page;

public interface CustomGoodsRegReqRepository {
	
	Page<GoodsRegReq> getSearchPage(SearchParamDto requestDto, boolean isAdmin);
}
