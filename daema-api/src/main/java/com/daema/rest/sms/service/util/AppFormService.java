package com.daema.rest.sms.service.util;

import com.daema.core.scm.dto.request.AppFormCreateDto;
import com.daema.core.scm.dto.request.AppFormInquiryDto;
import com.daema.core.scm.dto.request.AppFormUpdateDto;
import com.daema.core.scm.dto.response.AppFormRepDto;
import com.daema.rest.common.enums.ResponseCodeEnum;

public interface AppFormService {
    /**
     * 신청서 작성
     * @param appFormUpdateDto
     * @return
     */
    ResponseCodeEnum createAppForm(AppFormCreateDto appFormCreateDto);

    /**
     * 신청서 조회
     * @param appFormInquiryDto
     * @return
     */
    AppFormRepDto getAppForm(AppFormInquiryDto appFormInquiryDto);

    /**
     * 신청서 업데이트(수정)
     * @param appFormId
     * @param appFormUpdateDto
     * @return
     */
    ResponseCodeEnum updateAppForm(AppFormUpdateDto appFormUpdateDto);
}

