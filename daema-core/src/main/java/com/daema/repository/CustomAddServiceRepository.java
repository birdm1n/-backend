package com.daema.repository;

import com.daema.domain.AddService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAddServiceRepository {
	
	Page<AddService> getSearchPage(Pageable pageable, boolean isAdmin);
}
