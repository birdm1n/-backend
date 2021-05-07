package com.daema.rest.wms.service;

import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.response.DeviceResponseDto;
import com.daema.wms.domain.Device;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.InStockRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceMgmtService {

	private final InStockRepository stockRepository;
	private final DeviceRepository deviceRepository;
	private final AuthenticationUtil authenticationUtil;

	public DeviceMgmtService(InStockRepository stockRepository, DeviceRepository deviceRepository,
			AuthenticationUtil authenticationUtil) {
		this.stockRepository = stockRepository;
		this.deviceRepository = deviceRepository;
		this.authenticationUtil = authenticationUtil;
	}

	public DeviceResponseDto getDeviceInfoFromFullBarcode(String fullBarcode){
		Device device = deviceRepository.findByFullBarcode(fullBarcode);

		return new DeviceResponseDto();
	}

}