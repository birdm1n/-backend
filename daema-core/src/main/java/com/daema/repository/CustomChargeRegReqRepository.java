package com.daema.repository;

import com.daema.domain.ChargeRegReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomChargeRegReqRepository {
	
	Page<ChargeRegReq> getSearchPage(Pageable pageable, boolean isAdmin);
}
