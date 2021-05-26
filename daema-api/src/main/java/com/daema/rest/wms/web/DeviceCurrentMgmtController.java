package com.daema.rest.wms.web;

import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.service.DeviceCurrentMgmtService;
import com.daema.wms.domain.dto.request.DeviceCurrentRequestDto;
import com.daema.wms.domain.dto.response.DeviceCurrentResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "기기/기기현황 API", tags = "기기/기기현황 API")
@RestController
@RequestMapping(value = {"/v1/api/DeviceManagement/DeviceCurrentMgmt", "/api/DeviceManagement/DeviceCurrentMgmt" })
public class DeviceCurrentMgmtController {

    private final DeviceCurrentMgmtService deviceCurrentMgmtService;
    private final ResponseHandler responseHandler;

    public DeviceCurrentMgmtController(DeviceCurrentMgmtService deviceCurrentMgmtService, ResponseHandler responseHandler) {
        this.deviceCurrentMgmtService = deviceCurrentMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "기기 현황 목록 조회", notes = "기기 현황 목록을 조회합니다")
    @GetMapping("/getDeviceCurrentList")
    public ResponseEntity<CommonResponse<DeviceCurrentResponseDto>> getDeviceCurrentList(@ApiParam(value = "기기현황", required = true) DeviceCurrentRequestDto deviceCurrentRequestDto) {
       return responseHandler.getResponseMessageAsRetrieveResult(deviceCurrentMgmtService.getDeviceCurrentPage(deviceCurrentRequestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

}