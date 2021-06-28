package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.AddServiceRegReq;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

public interface CustomAddServiceRegReqRepository {
	
	Page<AddServiceRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
}
