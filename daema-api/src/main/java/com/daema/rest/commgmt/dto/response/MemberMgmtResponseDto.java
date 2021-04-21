package com.daema.rest.commgmt.dto.response;

import com.daema.rest.commgmt.dto.MemberMgmtDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberMgmtResponseDto {

    private List<MemberMgmtDto> userList;
}
