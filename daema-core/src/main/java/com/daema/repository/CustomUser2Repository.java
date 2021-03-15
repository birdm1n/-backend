package com.daema.repository;

import com.daema.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomUser2Repository {

	public Page<Board> getSearchPage(String type, String keyword, Pageable pageable);

}
