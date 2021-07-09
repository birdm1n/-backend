package com.daema.rest.sms.service;

import com.daema.core.sms.dto.response.ApplicationFormResponseDto;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.core.wms.dto.request.DeviceCurrentRequestDto;
import com.daema.core.wms.dto.response.DeviceCurrentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ApplicationFormService {

    private final AuthenticationUtil authenticationUtil;

 @Transactional(readOnly = true)
    public ResponseDto<ApplicationFormResponseDto> getApplication(DeviceCurrentRequestDto deviceCurrentRequestDto) {

        deviceCurrentRequestDto.setStoreId((authenticationUtil.getStoreId()));
        Page<DeviceCurrentResponseDto> responseDtoPage = deviceRepository.getDeviceCurrentPage(deviceCurrentRequestDto);
        return new ResponseDto(responseDtoPage);
    }

}

