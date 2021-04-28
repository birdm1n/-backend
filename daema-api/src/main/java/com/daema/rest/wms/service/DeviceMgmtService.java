package com.daema.rest.wms.service;

import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.repository.InStockRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceMgmtService {

	private final InStockRepository stockRepository;

	private final AuthenticationUtil authenticationUtil;

	public DeviceMgmtService(InStockRepository stockRepository, AuthenticationUtil authenticationUtil) {
		this.stockRepository = stockRepository;
		this.authenticationUtil = authenticationUtil;
	}


}