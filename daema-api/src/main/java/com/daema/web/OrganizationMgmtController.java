package com.daema.web;

import com.daema.common.Constants;
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

    @ApiOperation(value = "조직 목록 조회", notes = "조직 목록을 조회합니다", nickname = Constants.API_ORGNZT + "||1")
    @GetMapping("/getOrgnztList")
    public ResponseEntity<CommonResponse<OrganizationMgmtResponseDto>> getOrgnztList(OrganizationMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(organizationMgmtService.getOrgnztList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "조직 등록", notes = "신규 조직을 등록합니다", nickname = Constants.API_ORGNZT + "||2")
    @PostMapping("/insertOrgnzt")
    public ResponseEntity<CommonResponse<Void>> insertOrgnzt(@ApiParam(value = "조직 정보", required = true) @RequestBody OrganizationMgmtDto organizationMgmtDto) {
        organizationMgmtService.insertOrgnzt(organizationMgmtDto);
        return responseHandler.ok();
    }


    @ApiOperation(value = "조직 수정", notes = "조직의 내용을 변경합니다", nickname = Constants.API_ORGNZT + "||3")
    @PostMapping("/updateOrgnzt")
    public ResponseEntity<CommonResponse<Void>> updateOrgnzt(@ApiParam(value = "조직 정보", required = true) @RequestBody OrganizationMgmtDto organizationMgmtDto) {
        organizationMgmtService.updateOrgnzt(organizationMgmtDto, false);

        return responseHandler.ok();
    }

    @ApiOperation(value = "조직 삭제", notes = "조직을 삭제합니다", nickname = Constants.API_ORGNZT + "||4")
    @PostMapping("/deleteOrgnzt")
    public ResponseEntity<CommonResponse<Void>> deleteOrgnzt(@ApiParam(value = "조직 정보", required = true) @RequestBody OrganizationMgmtDto organizationMgmtDto) {
        organizationMgmtService.deleteOrgnzt(organizationMgmtDto);

        return responseHandler.ok();
    }

    @ApiOperation(value = "사용자 등록", notes = "신규 사용자를 등록합니다", nickname = Constants.API_USER + "||1")
    @PostMapping("/insertUser")
    public ResponseEntity<CommonResponse<Void>> insertUser(@ApiParam(value = "사용자 정보", required = true) @RequestBody OrganizationMemberDto organizationMemberDto) {
        organizationMgmtService.insertUser(organizationMemberDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "사용자 정보 수정", notes = "사용자의 정보를 변경합니다", nickname = Constants.API_USER + "||2")
    @PostMapping("/updateUser")
    public ResponseEntity<CommonResponse<Void>> updateUser(@ApiParam(value = "사용자 정보", required = true) @RequestBody OrganizationMemberDto organizationMemberDto) throws NotFoundException {
        organizationMgmtService.updateUser(organizationMemberDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "사용자 삭제", notes = "사용자를 제거합니다", nickname = Constants.API_USER + "||3")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "사용자 ID", required = true, example = "1", name = "delUserId", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/deleteUser")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@ApiIgnore @RequestBody ModelMap reqModel) {
        organizationMgmtService.deleteUser(reqModel);
        return responseHandler.ok();
    }

    @ApiOperation(value = "사용자 승인", notes = "사용자를 승인 처리합니다", nickname = Constants.API_USER + "||4")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "사용자 ID", required = true, example = "1", name = "userId", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/updateUserUse")
    public ResponseEntity<CommonResponse<Void>> updateUserUse(@ApiIgnore @RequestBody ModelMap reqModel) {
        organizationMgmtService.updateUserUse(reqModel);
        return responseHandler.ok();
    }
}


















