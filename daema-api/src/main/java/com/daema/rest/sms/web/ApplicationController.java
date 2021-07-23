package com.daema.rest.sms.web;

import com.daema.core.scm.dto.request.ApplicationCreateDto;
import com.daema.core.scm.dto.request.ApplicationInquiryDto;
import com.daema.core.scm.dto.request.ApplicationUpdateDto;
import com.daema.core.scm.dto.response.ApplicationReqDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.sms.service.util.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "판매관리/신청서작성 API", tags = "판매관리/신청서 API")
@RestController
@RequestMapping(value = {"/v1/api/scm/application", "/api/scm/application" })
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ResponseHandler responseHandler;

    public ApplicationController(ApplicationService applicationService, ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        this.applicationService = applicationService;

    }

    @ApiOperation(value = "신청서 작성", notes = "신청서를 작성 완료 처리 합니다.")
    @PostMapping("/insertApplication")
    public ResponseEntity<CommonResponse<Void>> insertApplication(@ApiParam(value = "신청서 작성 처리", required = true) @RequestBody ApplicationCreateDto applicationCreateDto) {

        ResponseCodeEnum responseCodeEnum = applicationService.createApplication(applicationCreateDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "신청서 조회", notes = "신청서 조회합니다.")
    @GetMapping("/inquiryApplication")
    public ResponseEntity<CommonResponse<ApplicationReqDto>> getApplication(@ApiParam(value = "신청서 조회", required = true) ApplicationInquiryDto applicationInquiryDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(applicationService.getApplication(applicationInquiryDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "신청서 수정", notes = "신청서를 수정 처리 합니다.")
    @PostMapping("/updateApplication")
    public ResponseEntity<CommonResponse<Void>> updateApplication(@ApiParam(value = "신청서 수정 처리", required = true) @RequestBody Long id, ApplicationUpdateDto applicationUpdateDto) {

        ResponseCodeEnum responseCodeEnum = applicationService.updateApplication(applicationUpdateDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "신청서 삭제", notes = "신청서 삭제처리.")
    @PostMapping("/deleteApplication")
    public ResponseEntity<CommonResponse<Void>> deleteApplication(@ApiParam(value = "신청서 삭제", required = true) @RequestBody ApplicationInquiryDto applicationInquiryDto) {

        ResponseCodeEnum responseCodeEnum = applicationService.deleteApplication(applicationInquiryDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }
}

