package com.daema.repository;

import com.daema.domain.Store;
import com.daema.domain.dto.common.SearchParamDto;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;

import java.util.List;
public interface CustomStoreRepository {
	
	Page<Store> getSearchPage(SearchParamDto requestDto);
	Store findStoreInfo(long parentStoreId, long storeId);
	List<Store> findBySaleStore(long parentStoreId, OrderSpecifier orderSpecifier);
}
