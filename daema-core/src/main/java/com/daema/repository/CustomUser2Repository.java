package com.daema.repository;

import com.daema.domain.Board;
import com.daema.domain.User2;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomUser2Repository {

	public Page<Board> getSearchPage(String type, String keyword, Pageable pageable);
	List<User2> findByUser(long storeId, OrderSpecifier orderSpecifier);
}
