package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.ChargeRegReq;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

public interface CustomChargeRegReqRepository {
	
	Page<ChargeRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
}
