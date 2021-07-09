package com.daema.rest.sms.web;

import com.daema.core.sms.dto.request.AppFormInquiryDto;
import com.daema.core.sms.dto.request.AppFormReqDto;
import com.daema.core.sms.dto.response.AppFormResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.sms.service.CustomerMgmtService;
import com.daema.rest.sms.service.AppFormService;
import com.daema.rest.sms.service.JoinInfoMgmtService;
import com.daema.rest.sms.service.PaymentMgmtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "판매관리/신청서작성 API", tags = "판매관리/신청서 API")
@RestController
@RequestMapping(value = {"/v1/api/SMS/FillOutApplicationMgmt", "/api/SMS/FillOutApplicationMgmt" })
public class AppFormController {
    private final AppFormService appFormService;


    private final ResponseHandler responseHandler;

    public AppFormController(AppFormService appFormService, JoinInfoMgmtService joinInfoMgmtService, PaymentMgmtService paymentMgmtService, CustomerMgmtService customerMgmtService, ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        this.appFormService = appFormService;

    }

    @ApiOperation(value = "신청서 작성", notes = "신청서를 작성 완료 처리 합니다.")
    @PostMapping("/insertApplication")
    public ResponseEntity<CommonResponse<Void>> insertApplication(@ApiParam(value = "신청서 작성 처리", required = true) @RequestBody AppFormReqDto appFormReqDto) {

        ResponseCodeEnum responseCodeEnum = appFormService.insertApplication(appFormReqDto);
        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "신청서 조회", notes = "신청서 조회합니다.")
    @PostMapping("/inquiryApplication")
    public ResponseEntity<CommonResponse<AppFormResponseDto>> getApplication(@ApiParam(value = "신청서 조회", required = true) AppFormInquiryDto appFormInquiryDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(appFormService.getApplication(appFormInquiryDto) , ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

}
