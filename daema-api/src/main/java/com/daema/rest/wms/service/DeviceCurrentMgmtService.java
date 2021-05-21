package com.daema.rest.wms.service;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.wms.domain.dto.request.DeviceCurrentRequestDto;
import com.daema.wms.domain.dto.response.DeviceCurrentResponseDto;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.InStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeviceCurrentMgmtService {

	private final DeviceRepository deviceRepository;
	private final AuthenticationUtil authenticationUtil;


	@Transactional(readOnly = true)
	public ResponseDto<DeviceCurrentResponseDto> getDeviceCurrentPage(DeviceCurrentRequestDto deviceCurrentRequestDto) {

		deviceCurrentRequestDto.setStoreId((authenticationUtil.getStoreId()));
		Page<DeviceCurrentResponseDto> responseDtoPage = deviceRepository.getDeviceCurrentPage(deviceCurrentRequestDto);
		return new ResponseDto(responseDtoPage);
	}
}

