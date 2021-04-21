package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.GoodsRegReq;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

public interface CustomGoodsRegReqRepository {
	
	Page<GoodsRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
}
