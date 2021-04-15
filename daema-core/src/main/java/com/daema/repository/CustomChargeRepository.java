package com.daema.repository;

import com.daema.domain.Charge;
import com.daema.domain.dto.common.SearchParamDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomChargeRepository {
	
	Page<Charge> getSearchPage(SearchParamDto requestDto, boolean isAdmin);
	List<Charge> getMatchList();
}
