package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.ChargeRegReq;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

public interface CustomChargeRegReqRepository {
	
	Page<ChargeRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
}
