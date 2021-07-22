package com.daema.rest.sms.service.impl;

import com.daema.core.scm.domain.appform.AppForm;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.request.AppFormCreateDto;
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


    @Transactional
    public ResponseCodeEnum createAppForm(AppFormCreateDto appFormCreateDto) {
        Long membersId = authenticationUtil.getMemberSeq();
        appFormCreateDto.getBasicInfoDto().setMembersId(membersId);
        appFormCreateDto.getBasicInfoDto().setConsultState(ScmEnum.TaskState.ConsultState.RCPT_ING);

        AppForm appForm = AppForm.create(appFormCreateDto);

        if (appForm == null) {
            return ResponseCodeEnum.FAIL;
        }

        appFormRepository.save(appForm);
        return ResponseCodeEnum.OK;
    }

    @Override
    @Transactional(readOnly = true)
    public AppFormRepDto getAppForm(AppFormInquiryDto appFormInquiryDto) {

        AppForm appForm = appFormRepository.findById((appFormInquiryDto.getAppFormId())).orElseGet(null);

        return AppForm.repAppForm(appForm);

    }

    @Override
    @Transactional
    public ResponseCodeEnum updateAppForm(AppFormUpdateDto appFormUpdateDto) {
    /*    Long membersId = authenticationUtil.getMemberSeq();
        appFormUpdateDto.getBasicInfoDto().setMembers(membersId);
   appForm.getCustomer().updateCustomer(appFormUpdateDto.getCustomerDto());
*/
        Long Id = appFormUpdateDto.getBasicInfoDto().getAppFormId();

        AppForm appForm = appFormRepository.findById(Id).orElseGet(null);
        if(appForm != null){
            AppForm.updateAppForm(appForm, appFormUpdateDto);
        }
        return ResponseCodeEnum.OK;
    }

    @Override
    @Transactional
    public ResponseCodeEnum deleteAppForm(AppFormInquiryDto appFormInquiryDto) {
     /*   appFormRepository.deleteById(appFormInquiryDto.getAppFormId());*/
        appFormRepository.deleteAll();
        return ResponseCodeEnum.OK;
    }
}


