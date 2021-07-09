package com.daema.rest.sms.service;

import com.daema.core.base.domain.Members;
import com.daema.core.sms.domain.*;
import com.daema.core.sms.dto.CustomerDto;
import com.daema.core.sms.dto.JoinInfoDto;
import com.daema.core.sms.dto.PaymentDto;
import com.daema.core.sms.dto.request.AppFormInquiryDto;
import com.daema.core.sms.dto.request.AppFormReqDto;
import com.daema.core.sms.dto.response.AppFormResponseDto;
import com.daema.core.sms.repository.AppFormRepository;
import com.daema.core.sms.repository.CustomerRepository;
import com.daema.core.sms.repository.JoinInfoRepository;
import com.daema.core.sms.repository.PaymentRepository;
import com.daema.core.wms.domain.Device;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.core.wms.dto.request.DeviceCurrentRequestDto;
import com.daema.core.wms.dto.response.DeviceCurrentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppFormService {

    private final AppFormRepository appFormRepository;
    private final AuthenticationUtil authenticationUtil;

@Transactional
public ResponseCodeEnum insertApplication(AppFormReqDto appFormReqDto){
    Long membersId = authenticationUtil.getMemberSeq();
    appFormReqDto.getBasicInfoDto().setMembers(membersId);

    AppForm appForm = AppForm.toEntity(appFormReqDto);

    if(appForm == null) {
        return ResponseCodeEnum.FAIL;
    }

    appFormRepository.save(appForm);
    return ResponseCodeEnum.OK;
}

 @Transactional(readOnly = true)
    public AppFormResponseDto getApplication(AppFormInquiryDto appFormInquiryDto) {

        AppForm appForm = appFormRepository.findById((appFormInquiryDto.getAppFormId())).orElseGet(null);

        return AppForm.responseFrom(appForm);

    }

}

