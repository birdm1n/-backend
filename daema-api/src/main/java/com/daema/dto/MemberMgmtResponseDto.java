package com.daema.dto;

import com.daema.dto.common.PagingDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberMgmtResponseDto extends PagingDto {

    private List<MemberMgmtDto> userList;
}
