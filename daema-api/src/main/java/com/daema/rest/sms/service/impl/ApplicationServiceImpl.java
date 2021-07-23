package com.daema.rest.sms.service.impl;

import com.daema.core.scm.domain.application.Application;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.request.ApplicationCreateDto;
import com.daema.core.scm.dto.request.ApplicationInquiryDto;
import com.daema.core.scm.dto.request.ApplicationUpdateDto;
import com.daema.core.scm.dto.response.ApplicationReqDto;
import com.daema.core.scm.repository.util.ApplicationRepoistory;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.sms.service.util.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepoistory applicationRepoistory;
    private final AuthenticationUtil authenticationUtil;


    @Transactional
    public ResponseCodeEnum createApplication(ApplicationCreateDto applicationCreateDto) {
        Long membersId = authenticationUtil.getMemberSeq();
        applicationCreateDto.getApplicationBasicDto().setMembersId(membersId);
        applicationCreateDto.getApplicationBasicDto().setConsultState(ScmEnum.TaskState.ConsultState.RCPT_ING);

        Application application = Application.create(applicationCreateDto);

        if (application == null) {
            return ResponseCodeEnum.FAIL;
        }

        applicationRepoistory.save(application);
        return ResponseCodeEnum.OK;
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationReqDto getApplication(ApplicationInquiryDto applicationInquiryDto) {

        Application application = applicationRepoistory.findById((applicationInquiryDto.getApplId())).orElseGet(null);

        return Application.repApplication(application);

    }

    @Override
    @Transactional
    public ResponseCodeEnum updateApplication(ApplicationUpdateDto applicationUpdateDto) {
    /*    Long membersId = authenticationUtil.getMemberSeq();
        appFormUpdateDto.getBasicInfoDto().setMembers(membersId);
   appForm.getCustomer().updateCustomer(appFormUpdateDto.getCustomerDto());
*/
        Long Id = applicationUpdateDto.getApplicationBasicDto().getApplId();

        Application application = applicationRepoistory.findById(Id).orElseGet(null);
        if(application != null){
            Application.updateApplication(application, applicationUpdateDto);
        }
        return ResponseCodeEnum.OK;
    }

    @Override
    @Transactional
    public ResponseCodeEnum deleteApplication(ApplicationInquiryDto applicationInquiryDto) {
     /*   appFormRepository.deleteById(appFormInquiryDto.getAppFormId());*/
        applicationRepoistory.deleteAll();
        return ResponseCodeEnum.OK;
    }
}


