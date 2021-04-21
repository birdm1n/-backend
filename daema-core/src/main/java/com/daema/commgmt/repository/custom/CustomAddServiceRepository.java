package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.AddService;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

public interface CustomAddServiceRepository {
	
	Page<AddService> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
}
