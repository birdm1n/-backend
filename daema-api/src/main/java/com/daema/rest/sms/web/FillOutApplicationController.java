package com.daema.rest.sms.web;

import com.daema.core.sms.dto.request.FillOutApplicationRequestDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.core.sms.domain.Payment;
import com.daema.rest.sms.service.CustomerMgmtService;
import com.daema.rest.sms.service.FillOutApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "판매관리/신청서작성 API", tags = "판매관리/신청서 API")
@RestController
@RequestMapping(value = {"/v1/api/SMS/FillOutApplicationMgmt", "/api/SMS/FillOutApplicationMgmt" })
public class FillOutApplicationController {
    private final CustomerMgmtService customerMgmtService;
    private final FillOutApplicationService fillOutApplicationService;
    private final ResponseHandler responseHandler;

    public FillOutApplicationController(FillOutApplicationService fillOutApplicationService, CustomerMgmtService customerMgmtService,ResponseHandler responseHandler) {
        this.fillOutApplicationService = fillOutApplicationService;
        this.customerMgmtService = customerMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "신청서 작성", notes = "신청서를 작성 완료 처리 합니다.")
    @PostMapping("/insertApplication")
    public ResponseEntity<CommonResponse<Void>> insertApplication(@ApiParam(value = "신청서 작성 처리", required = true) @RequestBody FillOutApplicationRequestDto fillOutApplicationRequestDto) {
     ResponseCodeEnum responseCodeEnum = customerMgmtService.insertCustomer(fillOutApplicationRequestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

}
