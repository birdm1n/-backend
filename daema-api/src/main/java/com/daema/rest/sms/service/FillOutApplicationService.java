package com.daema.rest.sms.service;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.sms.domain.Account;
import com.daema.core.sms.domain.Payment;
import com.daema.core.sms.domain.enums.SmsEnum;
import com.daema.core.sms.repository.PaymentRepository;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.core.wms.dto.request.OpeningInsertReqDto;
import com.daema.core.wms.domain.Device;
import com.daema.core.wms.domain.Opening;
import com.daema.core.wms.dto.request.DeviceCurrentRequestDto;
import com.daema.core.wms.dto.response.DeviceCurrentResponseDto;
import com.daema.core.wms.repository.DeviceRepository;
import com.daema.core.wms.repository.OpeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FillOutApplicationService {

    private final PaymentRepository paymentRepository;
    private final AuthenticationUtil authenticationUtil;

/* @Transactional(readOnly = true)
    public ResponseDto<DeviceCurrentResponseDto> getDeviceCurrentPage(DeviceCurrentRequestDto deviceCurrentRequestDto) {

        deviceCurrentRequestDto.setStoreId((authenticationUtil.getStoreId()));
        Page<DeviceCurrentResponseDto> responseDtoPage = deviceRepository.getDeviceCurrentPage(deviceCurrentRequestDto);
        return new ResponseDto(responseDtoPage);
    }*/

}

