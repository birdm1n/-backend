package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.OpenStore;
import com.daema.commgmt.domain.dto.response.OpenStoreListDto;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomOpenStoreRepository {
	
	Page<OpenStoreListDto> getSearchPage(ComMgmtRequestDto requestDto);
	List<OpenStoreListDto> getOpenStoreList(ComMgmtRequestDto requestDto);
	OpenStore findOpenStoreInfo(long openStoreId, long storeId);
}
