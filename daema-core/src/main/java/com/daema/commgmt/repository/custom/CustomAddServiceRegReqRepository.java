package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.AddServiceRegReq;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

public interface CustomAddServiceRegReqRepository {
	
	Page<AddServiceRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
}
