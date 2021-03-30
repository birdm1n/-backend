package com.daema.repository;

import com.daema.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomGoodsRepository {
	
	Page<Goods> getSearchPage(Pageable pageable, boolean isAdmin);
	List<Goods> getMatchList();
}
