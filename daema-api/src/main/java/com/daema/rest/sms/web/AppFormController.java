package com.daema.rest.sms.web;

import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.request.AppFormInquiryDto;
import com.daema.core.scm.dto.request.AppFormUpdateDto;
import com.daema.core.scm.dto.response.AppFormRepDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.sms.service.util.AppFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "판매관리/신청서작성 API", tags = "판매관리/신청서 API")
@RestController
@RequestMapping(value = {"/v1/api/SMS/FillOutApplicationMgmt", "/api/SMS/FillOutApplicationMgmt" })
public class AppFormController {
    private final AppFormService appFormService;
    private final ResponseHandler responseHandler;

    public AppFormController(AppFormService appFormService, ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        this.appFormService = appFormService;

    }

    @ApiOperation(value = "신청서 작성", notes = "신청서를 작성 완료 처리 합니다.")
    @PostMapping("/insertApplication")
    public ResponseEntity<CommonResponse<Void>> insertApplication(@ApiParam(value = "신청서 작성 처리", required = true) @RequestBody AppFormUpdateDto appFormUpdateDto) {

        ResponseCodeEnum responseCodeEnum = appFormService.insertApplication(appFormUpdateDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "신청서 조회", notes = "신청서 조회합니다.")
    @PostMapping("/inquiryApplication")
    public ResponseEntity<CommonResponse<AppFormRepDto>> getApplication(@ApiParam(value = "신청서 조회", required = true) AppFormInquiryDto appFormInquiryDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(appFormService.getApplication(appFormInquiryDto) , ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "신청서 수정", notes = "신청서를 수정 처리 합니다.")
    @PostMapping("/updateApplication")
    public ResponseEntity<CommonResponse<Void>> updateApplication(@ApiParam(value = "신청서 수정 처리", required = true) @RequestBody Long id, AppFormUpdateDto appFormUpdateDto) {

        ResponseCodeEnum responseCodeEnum = appFormService.updateApplication(id, appFormUpdateDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

}
