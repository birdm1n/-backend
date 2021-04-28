package com.daema.rest.wms.service;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.InStockMgmtDto;
import com.daema.rest.wms.dto.ProviderMgmtDto;
import com.daema.rest.wms.dto.response.InStockMgmtResponseDto;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.StockListDto;
import com.daema.wms.domain.dto.response.WaitInStockDto;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.InStockRepository;
import com.daema.wms.repository.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InStockMgmtService {

	private final InStockRepository inStockRepository;
	private final DeviceRepository deviceRepository;
	private final AuthenticationUtil authenticationUtil;

	public InStockMgmtService(InStockRepository inStockRepository, DeviceRepository deviceRepository, AuthenticationUtil authenticationUtil) {
		this.inStockRepository = inStockRepository;
		this.deviceRepository = deviceRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public List<WaitInStockDto> getWaitInStockList(InStockRequestDto requestDto) {
		requestDto.setStoreId(authenticationUtil.getStoreId());
		List<WaitInStockDto> dataList = inStockRepository.getWaitInStockList(requestDto);

		return dataList;
	}
}