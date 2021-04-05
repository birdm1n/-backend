package com.daema.web;

import com.daema.dto.OrganizationMemberDto;
import com.daema.dto.OrganizationMgmtDto;
import com.daema.dto.OrganizationMgmtRequestDto;
import com.daema.dto.OrganizationMgmtResponseDto;
import com.daema.response.enums.ResponseCodeEnum;
import com.daema.response.handler.ResponseHandler;
import com.daema.response.io.CommonResponse;
import com.daema.service.OrganizationMgmtService;
import io.swagger.annotations.*;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "조직 관리 API", tags = "조직 관리 API")
@RestController
@RequestMapping("/v1/api/OrganizationManagement/OrganizationMgmt")
public class OrganizationMgmtController {

    private final OrganizationMgmtService organizationMgmtService;

    private final ResponseHandler responseHandler;

    public OrganizationMgmtController(OrganizationMgmtService organizationMgmtService, ResponseHandler responseHandler) {
        this.organizationMgmtService = organizationMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "조직 목록 조회", notes = "조직 목록을 조회합니다")
    @GetMapping("/getOrgnztList")
    public ResponseEntity<CommonResponse<OrganizationMgmtResponseDto>> getOrgnztList(OrganizationMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(organizationMgmtService.getOrgnztList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "조직 등록", notes = "신규 조직을 등록합니다")
    @PostMapping("/insertOrgnzt")
    public ResponseEntity<CommonResponse<Void>> insertOrgnzt(@ApiParam(value = "조직 정보", required = true) @RequestBody OrganizationMgmtDto organizationMgmtDto) {
        organizationMgmtService.insertOrgnzt(organizationMgmtDto);
        return responseHandler.ok();
    }


    @ApiOperation(value = "조직 수정", notes = "특정 조직의 내용을 변경합니다")
    @PostMapping("/updateOrgnzt")
    public ResponseEntity<CommonResponse<Void>> updateOrgnzt(@ApiParam(value = "조직 정보", required = true) @RequestBody OrganizationMgmtDto organizationMgmtDto) {
        organizationMgmtService.updateOrgnzt(organizationMgmtDto, false);

        return responseHandler.ok();
    }

    @ApiOperation(value = "조직 삭제", notes = "특정 조직을 삭제합니다")
    @PostMapping("/deleteOrgnzt")
    public ResponseEntity<CommonResponse<Void>> deleteOrgnzt(@ApiParam(value = "조직 정보", required = true) @RequestBody OrganizationMgmtDto organizationMgmtDto) {
        organizationMgmtService.deleteOrgnzt(organizationMgmtDto);

        return responseHandler.ok();
    }

    @ApiOperation(value = "사용자 등록", notes = "신규 사용자를 등록합니다")
    @PostMapping("/insertUser")
    public ResponseEntity<CommonResponse<Void>> insertUser(@ApiParam(value = "사용자 정보", required = true) @RequestBody OrganizationMemberDto organizationMemberDto) {
        organizationMgmtService.insertUser(organizationMemberDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "사용자 정보 수정", notes = "특정 사용자의 정보를 변경합니다")
    @PostMapping("/updateUser")
    public ResponseEntity<CommonResponse<Void>> updateUser(@ApiParam(value = "사용자 정보", required = true) @RequestBody OrganizationMemberDto organizationMemberDto) throws NotFoundException {
        organizationMgmtService.updateUser(organizationMemberDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "사용자 삭제", notes = "사용자를 제거합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "사용자 ID", required = true, example = "1", name = "delUserId", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/deleteUser")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@ApiIgnore @RequestBody ModelMap reqModel) {
        organizationMgmtService.deleteUser(reqModel);
        return responseHandler.ok();
    }

    @ApiOperation(value = "사용자 승인", notes = "사용자를 승인 처리합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "사용자 ID", required = true, example = "1", name = "userId", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/updateUserUse")
    public ResponseEntity<CommonResponse<Void>> updateUserUse(@ApiIgnore @RequestBody ModelMap reqModel) {
        organizationMgmtService.updateUserUse(reqModel);
        return responseHandler.ok();
    }

    /*
    @ApiOperation(value = "특정 상품,요금의 조직 히스토리 조회", notes = "특정 상품,요금의 조직 히스토리를 조회합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "상품 ID", required = true, example = "1", name = "goodsId")
            ,@ApiImplicitParam(value = "요금 ID", required = true, example = "1", name = "chargeId")
    })
    @GetMapping("/getHistoryList")
    public ResponseEntity<CommonResponse<ResponseDto<OrganizationMgmtDto>>> getHistoryList(@ApiParam(value = "요금 ID", required = true, example = "1") @RequestParam long chargeId
            , @ApiParam(value = "상품 ID", required = true, example = "1") @RequestParam long goodsId) {
        return responseHandler.getResponseMessageAsRetrieveResult(organizationMgmtService.getHistoryList(chargeId, goodsId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }


    @ApiOperation(value = "조직 삭제", notes = "특정 조직을 삭제합니다")
    @ApiImplicitParam(value = "조직 ID", required = true, example = "1", name = "organizationId")
    @PostMapping("/deleteData")
    public ResponseEntity<CommonResponse<Void>> deleteData(@ApiIgnore @RequestBody ModelMap modelMap) {

        organizationMgmtService.deleteData(modelMap);

        return responseHandler.ok();
    }

    @ApiOperation(value = "조직 원천 데이터 목록 조회", notes = "조직 원천 데이터 목록을 조회합니다")
    @GetMapping("/getRawDataList")
    public ResponseEntity<CommonResponse<OrganizationMgmtResponseDto>> getRawDataList() {
        return responseHandler.getResponseMessageAsRetrieveResult(organizationMgmtService.getRawDataList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    */
}


















