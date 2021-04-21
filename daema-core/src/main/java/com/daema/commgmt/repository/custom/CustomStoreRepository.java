package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.Store;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;

import java.util.List;
public interface CustomStoreRepository {
	
	Page<Store> getSearchPage(ComMgmtRequestDto requestDto);
	Store findStoreInfo(long parentStoreId, long storeId);
	List<Store> findBySaleStore(long parentStoreId, OrderSpecifier orderSpecifier);
}
