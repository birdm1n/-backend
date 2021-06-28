package com.daema.core.sample.repository;

import com.daema.core.sample.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

}
