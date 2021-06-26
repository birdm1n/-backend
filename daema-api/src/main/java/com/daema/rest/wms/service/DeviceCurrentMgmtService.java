package com.daema.rest.wms.service;

import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.request.OpeningInsertReqDto;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.Opening;
import com.daema.wms.domain.dto.request.DeviceCurrentRequestDto;
import com.daema.wms.domain.dto.response.DeviceCurrentResponseDto;
import com.daema.wms.repository.DeviceRepository;
import com.daema.wms.repository.OpeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeviceCurrentMgmtService {

	private final DeviceRepository deviceRepository;
	private final AuthenticationUtil authenticationUtil;
	private final OpeningRepository openingRepository;

	@Transactional(readOnly = true)
	public ResponseDto<DeviceCurrentResponseDto> getDeviceCurrentPage(DeviceCurrentRequestDto deviceCurrentRequestDto) {

		deviceCurrentRequestDto.setStoreId((authenticationUtil.getStoreId()));
		Page<DeviceCurrentResponseDto> responseDtoPage = deviceRepository.getDeviceCurrentPage(deviceCurrentRequestDto);
		return new ResponseDto(responseDtoPage);
	}

	@Transactional
	public ResponseCodeEnum insertOpening(OpeningInsertReqDto requestDto) {
		long storeId = authenticationUtil.getStoreId();
		Store store = Store.builder()
				.storeId(storeId)
				.build();
		Device device = deviceRepository.findById(requestDto.getDvcId()).orElse(null);
		if(device == null){
			return ResponseCodeEnum.NO_STORE_STOCK;
		}

		Opening opening = Opening.builder().build();
		opening.insertOpening(store, device, requestDto.getOpeningDate(), requestDto.getOpeningMemo());
		openingRepository.save(opening);
		return ResponseCodeEnum.OK;
	}
}

