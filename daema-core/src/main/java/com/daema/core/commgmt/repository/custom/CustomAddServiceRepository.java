package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.AddService;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

public interface CustomAddServiceRepository {
	
	Page<AddService> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
}
