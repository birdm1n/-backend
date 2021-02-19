package com.daema.service;

import com.daema.domain.Board;
import com.daema.dto.BoardDto;
import com.daema.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public List<Board> getBoardList() {
		return boardRepository.findAll();
	}

	public String saveBoard(BoardDto boardDto) throws Exception {

		String retVal;

		try {

			// 강제 오류 발생. 예외 처리 테스트
			// RaiseException.numberFormatException();

			Optional<Board> boardInfo = boardRepository.findById(boardDto.getBoardNo());

			if (!boardInfo.isPresent()) {
				Board board = new Board();
				board.setTitle(boardDto.getTitle());
				board.setWriter(boardDto.getWriter());
				board.setContent(boardDto.getContent());

				boardRepository.save(board);

				retVal = "success";
			} else {
				retVal = "isPresent";
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}

		return retVal;
	}

	public Board getBoardDetail(String boardNo) {
		return boardRepository.findById(Long.parseLong(boardNo)).orElseGet(() -> null);
	}

	public String updateBoard(BoardDto boardDto) {

		String retVal = "false";

		try {
			Optional<Board> boardInfo = boardRepository.findById(boardDto.getBoardNo());

			if (boardInfo.isPresent()) {

				Board board = new Board();
				board.setBoardNo(boardDto.getBoardNo());
				board.setTitle(boardDto.getTitle());
				board.setWriter(boardDto.getWriter());
				board.setContent(boardDto.getContent());

				boardRepository.save(board);

				retVal = "success";
			} else {
				retVal = "isNotPresent";
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return retVal;
	}

	public String deleteBoard(String boardNo) {

		String retVal = "false";

		try {
			Optional<Board> boardInfo = boardRepository.findById(Long.parseLong(boardNo));

			if (boardInfo.isPresent()) {
				boardRepository.deleteById(Long.parseLong(boardNo));
				retVal = "success";
			} else {
				retVal = "isNotPresent";
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return retVal;
	}
}
