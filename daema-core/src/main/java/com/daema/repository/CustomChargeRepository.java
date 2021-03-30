package com.daema.repository;

import com.daema.domain.Charge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomChargeRepository {
	
	Page<Charge> getSearchPage(Pageable pageable, boolean isAdmin);
	List<Charge> getMatchList();
}
