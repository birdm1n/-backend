package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.GoodsRegReq;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

public interface CustomGoodsRegReqRepository {
	
	Page<GoodsRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
}
