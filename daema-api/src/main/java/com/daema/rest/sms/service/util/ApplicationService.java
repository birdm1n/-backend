package com.daema.rest.sms.service.util;

import com.daema.core.scm.dto.request.ApplicationCreateDto;
import com.daema.core.scm.dto.request.ApplicationInquiryDto;
import com.daema.core.scm.dto.request.ApplicationUpdateDto;
import com.daema.core.scm.dto.response.ApplicationReqDto;
import com.daema.rest.common.enums.ResponseCodeEnum;

public interface ApplicationService {
    /**
     * 신청서 작성
     * @param applicationCreateDto
     * @return
     */
    ResponseCodeEnum createApplication(ApplicationCreateDto applicationCreateDto);

    /**
     * 신청서 조회
     * @param applicationInquiryDto
     * @return
     */
    ApplicationReqDto getApplication(ApplicationInquiryDto applicationInquiryDto);

    /**
     * 신청서 수정
     * @param applicationUpdateDto
     * @return
     */
    ResponseCodeEnum updateApplication(ApplicationUpdateDto applicationUpdateDto);

    /**
     * 신청서 삭제
     * @param applicationInquiryDto
     * @return
     */
    ResponseCodeEnum deleteApplication(ApplicationInquiryDto applicationInquiryDto);
}

