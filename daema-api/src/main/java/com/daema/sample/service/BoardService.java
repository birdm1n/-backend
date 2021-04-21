package com.daema.sample.service;

import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.sample.domain.Board;
import com.daema.sample.dto.BoardDto;
import com.daema.sample.dto.response.BoardResponseDto;
import com.daema.sample.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public BoardResponseDto getBoardList() {

		List<Board> boardList = boardRepository.findAll();

		BoardResponseDto boardResponseDto = new BoardResponseDto();
		boardResponseDto.setTotalCnt(boardList.size());
		boardResponseDto.setBoardList(boardList.stream()
				.map(BoardDto::new)
				//.map(boardEntity -> BoardDto.from(boardEntity))
				.collect(Collectors.toList()));

		return boardResponseDto;
	}

	public String saveBoard(BoardDto boardDto) throws Exception {

		String retVal;

		try {

			// 강제 오류 발생. 예외 처리 테스트
			// RaiseException.numberFormatException();

			Board board = new Board();
			board.setTitle(boardDto.getTitle());
			board.setWriter(boardDto.getWriter());
			board.setContent(boardDto.getContent());

			boardRepository.save(board);

			retVal = ServiceReturnMsgEnum.SUCCESS.name();

		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}

		return retVal;
	}

	public BoardDto getBoardDetail(String boardNo) {
		Board board = boardRepository.findById(Long.parseLong(boardNo)).orElse(null);

		return board != null ? BoardDto.from(board) : null;
	}

	public String updateBoard(BoardDto boardDto) {

		String retVal = ServiceReturnMsgEnum.FALSE.name();

		try {
			Optional<Board> boardInfo = boardRepository.findById(boardDto.getBoardNo());

			if (boardInfo.isPresent()) {

				//Board.updateBoard(boardDto);
				boardInfo.get().setBoardNo(boardDto.getBoardNo());
				boardInfo.get().setTitle(boardDto.getTitle());
				boardInfo.get().setWriter(boardDto.getWriter());
				boardInfo.get().setContent(boardDto.getContent());

				boardRepository.flush();

				retVal = ServiceReturnMsgEnum.SUCCESS.name();
			} else {
				retVal = ServiceReturnMsgEnum.IS_NOT_PRESENT.name();
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return retVal;
	}

	public String deleteBoard(String boardNo) {

		String retVal = ServiceReturnMsgEnum.FALSE.name();

		try {
			Optional<Board> boardInfo = boardRepository.findById(Long.parseLong(boardNo));

			if (boardInfo.isPresent()) {
				boardRepository.deleteById(Long.parseLong(boardNo));
				retVal = ServiceReturnMsgEnum.SUCCESS.name();
			} else {
				retVal = ServiceReturnMsgEnum.IS_NOT_PRESENT.name();
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return retVal;
	}
}
