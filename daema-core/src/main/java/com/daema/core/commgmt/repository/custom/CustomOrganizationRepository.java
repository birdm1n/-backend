package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;

import java.util.HashMap;
import java.util.List;

public interface CustomOrganizationRepository {

	HashMap<String, List> getOrgnztAndMemberList(ComMgmtRequestDto requestDto);
}
