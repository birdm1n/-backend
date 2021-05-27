package com.daema.rest.commgmt.web;

import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.AddServiceMgmtDto;
import com.daema.rest.commgmt.dto.AddServiceRegReqDto;
import com.daema.rest.commgmt.service.AddServiceMgmtService;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "부가서비스 관리 API", tags = "부가서비스 관리 API")
@RestController
@RequestMapping(value = {"/v1/api/AddServiceManagement/AddServiceMgmt", "/api/AddServiceManagement/AddServiceMgmt" })
public class AddServiceMgmtController {

    private final AddServiceMgmtService addServiceMgmtService;

    private final ResponseHandler responseHandler;

    public AddServiceMgmtController(AddServiceMgmtService addServiceMgmtService, ResponseHandler responseHandler) {
        this.addServiceMgmtService = addServiceMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "부가서비스 목록 조회", notes = "부가서비스 목록을 조회합니다", nickname = Constants.API_ADD_SERVICE + "||1")
    @GetMapping("/getList")
    public ResponseEntity<CommonResponse<ResponseDto<AddServiceMgmtDto>>> getList(ComMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(addServiceMgmtService.getList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "부가서비스 등록", notes = "신규 부가서비스을 등록합니다", nickname = Constants.API_ADD_SERVICE + "||2")
    @PostMapping("/insertData")
    public ResponseEntity<CommonResponse<Void>> insertData(@ApiParam(value = "부가서비스 정보", required = true) @RequestBody AddServiceMgmtDto addServiceMgmtDto) {
        addServiceMgmtService.insertData(addServiceMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "부가서비스 수정", notes = "부가서비스의 내용을 변경합니다", nickname = Constants.API_ADD_SERVICE + "||3")
    @PostMapping("/updateData")
    public ResponseEntity<CommonResponse<Void>> updateData(@ApiParam(value = "부가서비스 정보", required = true) @RequestBody AddServiceMgmtDto addServiceMgmtDto) {
        addServiceMgmtService.updateData(addServiceMgmtDto);

        return responseHandler.ok();
    }

    @ApiOperation(value = "부가서비스 삭제", notes = "부가서비스을 삭제합니다", nickname = Constants.API_ADD_SERVICE + "||4")
    @ApiImplicitParam(value = "부가서비스 ID", required = true, example = "1", name = "addSvcId", paramType = "query", allowMultiple = true)
    @PostMapping("/deleteData")
    public ResponseEntity<CommonResponse<Void>> deleteData(@ApiIgnore @RequestBody ModelMap reqModel) {

        addServiceMgmtService.deleteData(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "부가서비스 사용여부 수정", notes = "부가서비스의 사용 여부를 변경합니다", nickname = Constants.API_ADD_SERVICE + "||5")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "부가서비스 ID", required = true, example = "1", name = "addSvcId"),
            @ApiImplicitParam(value = "사용여부", required = true, name = "useYn")
    })
    @PostMapping("/updateUseYn")
    public ResponseEntity<CommonResponse<Void>> updateUseYn(@ApiIgnore @RequestBody ModelMap reqModel) {
        addServiceMgmtService.updateUseYn(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "부가서비스 등록 요청 조회", notes = "부가서비스 등록 요청 목록을 조회합니다", nickname = Constants.API_ADD_SERVICE + "||6")
    @GetMapping("/getRegReqList")
    public ResponseEntity<CommonResponse<ResponseDto<AddServiceRegReqDto>>> getRegReqList(ComMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(addServiceMgmtService.getRegReqList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "부가서비스 등록 요청 승인 및 반려", notes = "부가서비스 등록 요청건에 대해 처리합니다", nickname = Constants.API_ADD_SERVICE + "||7")
    @PostMapping("/updateReqStatus")
    public ResponseEntity<CommonResponse<Void>> updateReqStatus(@ApiParam(value = "부가서비스 등록 요청 건 처리", required = true) @RequestBody AddServiceRegReqDto addServiceRegReqDto) {
        addServiceMgmtService.updateReqStatus(addServiceRegReqDto);
        return responseHandler.ok();
    }
}


















