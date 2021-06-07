package com.daema.rest.wms.service;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.wms.domain.dto.request.OpeningCurrentRequestDto;
import com.daema.wms.domain.dto.response.OpeningCurrentResponseDto;
import com.daema.wms.repository.OpeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OpeningCurrentMgmtService {

	private final AuthenticationUtil authenticationUtil;
	private final OpeningRepository openingRepository;


	@Transactional(readOnly = true)
	public ResponseDto<OpeningCurrentResponseDto> getOpeningCurrentList(OpeningCurrentRequestDto openingCurrentRequestDto) {
		openingCurrentRequestDto.setStoreId((authenticationUtil.getStoreId()));
		Page<OpeningCurrentResponseDto> responseDtoPage = openingRepository.getOpeningCurrentList(openingCurrentRequestDto);
		return new ResponseDto(responseDtoPage);
	}
}

