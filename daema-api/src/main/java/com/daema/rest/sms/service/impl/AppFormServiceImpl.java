package com.daema.rest.sms.service.impl;

import com.daema.core.scm.domain.BasicInfo;
import com.daema.core.scm.domain.appform.AppForm;
import com.daema.core.scm.domain.customer.Customer;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.request.AppFormInquiryDto;
import com.daema.core.scm.dto.request.AppFormUpdateDto;
import com.daema.core.scm.dto.response.AppFormRepDto;
import com.daema.core.scm.repository.util.AppFormRepository;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.sms.service.util.AppFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AppFormServiceImpl implements AppFormService {

    private final AppFormRepository appFormRepository;
    private final AuthenticationUtil authenticationUtil;


    @Override
    @Transactional
    public ResponseCodeEnum insertApplication(AppFormUpdateDto appFormUpdateDto) {
        Long membersId = authenticationUtil.getMemberSeq();
        appFormUpdateDto.getBasicInfoDto().setMembers(membersId);
        appFormUpdateDto.setConsultState(ScmEnum.TaskState.ConsultState.RCPT_ING);

        AppForm appForm = AppForm.toEntity(appFormUpdateDto);

        if (appForm == null) {
            return ResponseCodeEnum.FAIL;
        }

        appFormRepository.save(appForm);
        return ResponseCodeEnum.OK;
    }

    @Override
    @Transactional(readOnly = true)
    public AppFormRepDto getApplication(AppFormInquiryDto appFormInquiryDto) {

        AppForm appForm = appFormRepository.findById((appFormInquiryDto.getAppFormId())).orElseGet(null);

        return AppForm.responseFrom(appForm);

    }

    @Override
    @Transactional
    public ResponseCodeEnum updateApplication(Long appFormId, AppFormUpdateDto appFormUpdateDto) {
    /*    Long membersId = authenticationUtil.getMemberSeq();
        appFormUpdateDto.getBasicInfoDto().setMembers(membersId);
   appForm.getCustomer().updateCustomer(appFormUpdateDto.getCustomerDto());
*/
        AppForm appForm = appFormRepository.findById(appFormId).orElseGet(null);
        if(appForm != null){
            AppForm.updateAppForm(appForm, appFormUpdateDto);
        }
        return ResponseCodeEnum.OK;
    }
}


