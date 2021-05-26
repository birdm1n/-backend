package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.ProviderMgmtDto;
import com.daema.rest.wms.service.ProviderMgmtService;
import com.daema.wms.domain.dto.request.ProviderRequestDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "공급처 관리 API", tags = "공급처 관리 API")
@RestController
@RequestMapping(value = {"/v1/api/ProviderManagement/ProviderMgmt", "/api/ProviderManagement/ProviderMgmt" })
public class ProviderMgmtController {

    private final ProviderMgmtService providerMgmtService;

    private final ResponseHandler responseHandler;

    public ProviderMgmtController(ProviderMgmtService providerMgmtService, ResponseHandler responseHandler) {
        this.providerMgmtService = providerMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "공급처 조회", notes = "공급처 목록을 조회합니다")
    @GetMapping("/getProviderList")
    public ResponseEntity<CommonResponse<ResponseDto<ProviderMgmtDto>>> getProviderList(ProviderRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(providerMgmtService.getProviderList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "공급처 등록", notes = "신규 공급처를 등록합니다")
    @PostMapping("/insertProvider")
    public ResponseEntity<CommonResponse<Void>> insertProvider(@ApiParam(value = "공급처 정보", required = true) @RequestBody ProviderMgmtDto providerMgmtDto) {
        providerMgmtService.insertProvider(providerMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "공급처 삭제", notes = "공급처를 삭제합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "공급처 ID", required = true, example = "1", name = "delProvId", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/deleteProvider")
    public ResponseEntity<CommonResponse<Void>> deleteProvider(@ApiIgnore @RequestBody ModelMap reqModel) {
        providerMgmtService.deleteProvider(reqModel);
        return responseHandler.ok();
    }

    @ApiOperation(value = "공급처 사용여부 수정", notes = "공급처의 사용 여부를 변경합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "공급처 ID", required = true, example = "1", name = "provId"),
            @ApiImplicitParam(value = "사용여부", required = true, name = "useYn")
    })
    @PostMapping("/updateProviderUse")
    public ResponseEntity<CommonResponse<Void>> updateProviderUse(@ApiIgnore @RequestBody ModelMap reqModel) {
        providerMgmtService.updateProviderUse(reqModel);
        return responseHandler.ok();
    }

    @ApiOperation(value = "공급처 수정", notes = "공급처의 내용을 변경합니다")
    @PostMapping("/updateProvider")
    public ResponseEntity<CommonResponse<Void>> updateProvider(@ApiParam(value = "공급처 정보", required = true) @RequestBody ProviderMgmtDto providerMgmtDto) {
        providerMgmtService.updateProviderInfo(providerMgmtDto);
        return responseHandler.ok();
    }
}


























