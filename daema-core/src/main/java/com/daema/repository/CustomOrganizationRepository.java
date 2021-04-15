package com.daema.repository;

import com.daema.domain.dto.common.SearchParamDto;

import java.util.HashMap;
import java.util.List;

public interface CustomOrganizationRepository {

	HashMap<String, List> getOrgnztAndMemberList(SearchParamDto requestDto);
}
