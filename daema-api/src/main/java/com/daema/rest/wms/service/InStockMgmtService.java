package com.daema.rest.wms.service;

import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.InStockRepository;
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

	public List<InStockWaitDto> getWaitInStockList(InStockRequestDto requestDto) {
		requestDto.setStoreId(authenticationUtil.getStoreId());
		List<InStockWaitDto> dataList = inStockRepository.getWaitInStockList(requestDto);

		return dataList;
	}

    public void insertWaitInStock(InStockWaitDto requestDto) {

//		inStockRepository.save(
//				InStockWait
//		);
    }
}