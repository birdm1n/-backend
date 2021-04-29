package com.daema.commgmt.repository.custom;

import com.daema.commgmt.domain.Charge;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomChargeRepository {
	
	Page<Charge> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin);
	List<Charge> getMatchList(ComMgmtRequestDto requestDto);
}
