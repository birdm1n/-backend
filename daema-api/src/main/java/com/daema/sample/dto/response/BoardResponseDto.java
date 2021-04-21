package com.daema.sample.dto.response;

import com.daema.base.dto.PagingDto;
import com.daema.sample.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardResponseDto extends PagingDto {

    private List<BoardDto> boardList;
}
