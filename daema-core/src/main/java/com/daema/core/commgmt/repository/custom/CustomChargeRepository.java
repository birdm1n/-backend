package com.daema.core.commgmt.repository.custom;

import com.daema.core.commgmt.domain.Charge;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomChargeRepository {
	
	Page<Charge> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
	List<Charge> getMatchList(ComMgmtRequestDto requestDto);
}
