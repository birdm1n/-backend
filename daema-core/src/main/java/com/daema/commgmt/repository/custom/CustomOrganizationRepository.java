package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;

import java.util.HashMap;
import java.util.List;

public interface CustomOrganizationRepository {

	HashMap<String, List> getOrgnztAndMemberList(ComMgmtRequestDto requestDto);
}
