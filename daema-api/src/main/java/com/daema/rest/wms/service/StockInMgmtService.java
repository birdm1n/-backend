package com.daema.rest.wms.service;

import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockInMgmtService {

	private final StockRepository stockRepository;

	private final AuthenticationUtil authenticationUtil;

	public StockInMgmtService(StockRepository stockRepository, AuthenticationUtil authenticationUtil) {
		this.stockRepository = stockRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public StockMgmtResponseDto getStockInList(StockRequestDto requestDto) {

		StockMgmtResponseDto responseDto = new StockMgmtResponseDto();

		return responseDto;
	}
}