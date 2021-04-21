package com.daema.sample.dto;

import com.daema.sample.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BoardDto {

	private Long boardNo;
	private String title;
	private String writer;
	private String content;

	public BoardDto(){

	}

	public BoardDto(Board board) {
	}

	public static BoardDto from (Board board){
		return BoardDto.builder()
				.boardNo(board.getBoardNo())
				.title(board.getTitle())
				.writer(board.getWriter())
				.content(board.getContent())
				.build();
	}
}
