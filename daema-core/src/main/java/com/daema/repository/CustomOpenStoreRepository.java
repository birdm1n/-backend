package com.daema.repository;

import com.daema.domain.OpenStore;
import com.daema.domain.dto.OpenStoreListDto;
import com.daema.domain.dto.common.SearchParamDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomOpenStoreRepository {
	
	Page<OpenStoreListDto> getSearchPage(SearchParamDto requestDto);
	List<OpenStoreListDto> getOpenStoreList(long storeId);
	OpenStore findOpenStoreInfo(long openStoreId, long storeId);
}
