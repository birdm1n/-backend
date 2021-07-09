package com.daema.rest.sms.web;

import com.daema.core.sms.dto.request.ApplicationFormInquiryDto;
import com.daema.core.sms.dto.request.ApplicationFormReqDto;
import com.daema.core.sms.dto.response.ApplicationFormResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.sms.service.CustomerMgmtService;
import com.daema.rest.sms.service.ApplicationFormService;
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
public class ApplicationFormController {
    private final CustomerMgmtService customerMgmtService;
    private final PaymentMgmtService paymentMgmtService;
    private final JoinInfoMgmtService joinInfoMgmtService;


    private final ResponseHandler responseHandler;

    public ApplicationFormController(JoinInfoMgmtService joinInfoMgmtService, PaymentMgmtService paymentMgmtService, CustomerMgmtService customerMgmtService, ResponseHandler responseHandler) {
        this.paymentMgmtService = paymentMgmtService;
        this.customerMgmtService = customerMgmtService;
        this.responseHandler = responseHandler;
        this.joinInfoMgmtService = joinInfoMgmtService;

    }

    @ApiOperation(value = "신청서 작성", notes = "신청서를 작성 완료 처리 합니다.")
    @PostMapping("/insertApplication")
    public ResponseEntity<CommonResponse<Void>> insertApplication(@ApiParam(value = "신청서 작성 처리", required = true) @RequestBody ApplicationFormReqDto fillOutApplicationRequestDto) {
        List<ResponseCodeEnum> responseCodeEnumList = new ArrayList<>();
     responseCodeEnumList.add(customerMgmtService.insertCustomer(fillOutApplicationRequestDto));
        responseCodeEnumList.add(paymentMgmtService.insertPayment(fillOutApplicationRequestDto));
        responseCodeEnumList.add(joinInfoMgmtService.insertJoinInfo(fillOutApplicationRequestDto));

        for(ResponseCodeEnum responseCodeEnum : responseCodeEnumList) {
            if (ResponseCodeEnum.OK != responseCodeEnum) {
                return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
            }
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "신청서 조회", notes = "신청서 조회합니다.")
    @PostMapping("/inquiryApplication")
    public ResponseEntity<CommonResponse<ApplicationFormResponseDto>> getFilloutApplication(@ApiParam(value = "신청서 조회", required = true) ApplicationFormInquiryDto applicationFormInquiryDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(ApplicationFormService.getDeviceCurrentPage(deviceCurrentRequestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

}
