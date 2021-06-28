package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;

import java.util.List;
public interface CustomStoreRepository {
	
	Page<Store> getSearchPage(ComMgmtRequestDto requestDto);
	Store findStoreInfo(long parentStoreId, long storeId);
	List<Store> findBySaleStore(long parentStoreId, OrderSpecifier orderSpecifier, Integer[] telecom);
}
