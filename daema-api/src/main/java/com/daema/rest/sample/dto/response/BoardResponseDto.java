package com.daema.rest.sample.dto.response;

import com.daema.core.base.dto.PagingDto;
import com.daema.rest.sample.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardResponseDto extends PagingDto {

    private List<BoardDto> boardList;
}
