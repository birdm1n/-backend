package com.daema.repository;

import com.daema.domain.Goods;
import com.daema.domain.dto.common.SearchParamDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomGoodsRepository {
	
	Page<Goods> getSearchPage(SearchParamDto requestDto, boolean isAdmin);
	List<Goods> getMatchList();
}
