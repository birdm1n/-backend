package com.daema.dto.response;

import com.daema.dto.BoardDto;
import com.daema.dto.common.PagingDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardResponseDto extends PagingDto {

    private List<BoardDto> boardList;
}
