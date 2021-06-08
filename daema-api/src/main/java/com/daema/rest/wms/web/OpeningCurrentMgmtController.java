package com.daema.rest.wms.web;

import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.request.OpeningUpdateReqDto;
import com.daema.rest.wms.service.OpeningCurrentMgmtService;
import com.daema.wms.domain.dto.request.OpeningCurrentRequestDto;
import com.daema.wms.domain.dto.response.OpeningCurrentResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Api(value = "기기관리/개통이력기기현황 API", tags = "기기관리/개통이력기기현황 API")
@RestController
@RequestMapping(value = {"/v1/api/DeviceManagement/OpeningCurrentMgmt", "/api/DeviceManagement/OpeningCurrentMgmt" })
public class OpeningCurrentMgmtController {

    private final OpeningCurrentMgmtService openingCurrentMgmtService;
    private final ResponseHandler responseHandler;


    @ApiOperation(value = "개통이력 기기현황 목록 조회", notes = "개통이력 기기현황 목록을 조회합니다")
    @GetMapping("/getOpeningCurrentList")
    public ResponseEntity<CommonResponse<OpeningCurrentResponseDto>> getOpeningCurrentList(@ApiParam(value = "개통이력 기기현황", required = true) OpeningCurrentRequestDto openingCurrentRequestDto) {
       return responseHandler.getResponseMessageAsRetrieveResult(openingCurrentMgmtService.getOpeningCurrentList(openingCurrentRequestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "개통 철회 처리", notes = "개통상태의 기기를 철회상태로 수정합니다")
    @PostMapping("/updateCancel")
    public ResponseEntity<CommonResponse<Void>> updateCancel(@RequestBody OpeningUpdateReqDto openingUpdateReqDto ) {
        openingCurrentMgmtService.updateCancel(openingUpdateReqDto);
        return responseHandler.ok();
    }

}