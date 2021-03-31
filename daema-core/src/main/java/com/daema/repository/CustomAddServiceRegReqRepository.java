package com.daema.repository;

import com.daema.domain.AddServiceRegReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAddServiceRegReqRepository {
	
	Page<AddServiceRegReq> getSearchPage(Pageable pageable, boolean isAdmin);
}
