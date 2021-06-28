package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.commgmt.dto.response.OpenStoreListDto;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomOpenStoreRepository {
	
	Page<OpenStoreListDto> getSearchPage(ComMgmtRequestDto requestDto);
	List<OpenStoreListDto> getOpenStoreList(ComMgmtRequestDto requestDto);
	OpenStore findOpenStoreInfo(long openStoreId, long storeId);
}
