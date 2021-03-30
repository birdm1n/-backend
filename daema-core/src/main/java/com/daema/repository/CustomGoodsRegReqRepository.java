package com.daema.repository;

import com.daema.domain.GoodsRegReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomGoodsRegReqRepository {
	
	Page<GoodsRegReq> getSearchPage(Pageable pageable, boolean isAdmin);
}
