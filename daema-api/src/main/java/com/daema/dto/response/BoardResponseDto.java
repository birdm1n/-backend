package com.daema.dto.response;

import com.daema.domain.dto.common.PagingDto;
import com.daema.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardResponseDto extends PagingDto {

    private List<BoardDto> boardList;
}
