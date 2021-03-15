package com.daema.repository;

import com.daema.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomStoreMapRepository {

	Page<Board> getSearchPage(String type, String keyword, Pageable pageable);
}
