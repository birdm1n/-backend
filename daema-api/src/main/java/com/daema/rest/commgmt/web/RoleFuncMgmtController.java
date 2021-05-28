package com.daema.rest.commgmt.web;

import com.daema.rest.commgmt.dto.FuncRoleMgmtDto;
import com.daema.rest.commgmt.dto.response.FuncRoleResponseDto;
import com.daema.rest.commgmt.service.RoleFuncMgmtService;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "역할 및 기능 관리 API", tags = "역할 및 기능 관리 API")
@RestController
@RequestMapping(value = {"/v1/api/RoleFuncManagement/RoleFuncMgmt", "/api/RoleFuncManagement/RoleFuncMgmt" })
public class RoleFuncMgmtController {

    private final RoleFuncMgmtService roleFuncMgmtService;
    private final AuthenticationUtil authenticationUtil;

    private final ResponseHandler responseHandler;

    public RoleFuncMgmtController(RoleFuncMgmtService roleFuncMgmtService, AuthenticationUtil authenticationUtil, ResponseHandler responseHandler) {
        this.roleFuncMgmtService = roleFuncMgmtService;
        this.authenticationUtil = authenticationUtil;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "역할 등록", notes = "신규 역할을 등록합니다", nickname = Constants.API_ROLE_FUNC + "||1")
    @PostMapping("/insertRole")
    public ResponseEntity<CommonResponse<Void>> insertRole(@ApiParam(value = "역할 정보", required = true) @RequestBody FuncRoleMgmtDto.RoleMgmtDto roleMgmtDto) {
        roleFuncMgmtService.insertRole(roleMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "역할 수정", notes = "역할의 내용을 변경합니다", nickname = Constants.API_ROLE_FUNC + "||2")
    @PostMapping("/updateRole")
    public ResponseEntity<CommonResponse<Void>> updateRole(@ApiParam(value = "역할 정보", required = true) @RequestBody FuncRoleMgmtDto.RoleMgmtDto roleMgmtDto) {
        roleFuncMgmtService.updateRole(roleMgmtDto);

        return responseHandler.ok();
    }

    @ApiOperation(value = "역할 삭제", notes = "역할을 삭제합니다", nickname = Constants.API_ROLE_FUNC + "||3")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "역할 ID", required = true, example = "1", name = "delRoleId"),
            @ApiImplicitParam(value = "관리점 ID", required = true, example = "1", name = "storeId")
    })
    @PostMapping("/deleteRole")
    public ResponseEntity<CommonResponse<Void>> deleteRole(@ApiIgnore @RequestBody ModelMap reqModel) {

        roleFuncMgmtService.deleteRole(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "역할과 기능 맵핑 조회", notes = "역할과 기능의 맵핑 데이터를 목록으로 조회합니다", nickname = Constants.API_ROLE_FUNC + "||4")
    @GetMapping("/getFuncRoleMapInfo")
    public ResponseEntity<CommonResponse<FuncRoleResponseDto>> getFuncRoleMapInfo(@ApiParam(value = "관리점 ID", required = true, example = "1") @RequestParam long storeId){
        return responseHandler.getResponseMessageAsRetrieveResult(roleFuncMgmtService.getFuncRoleMapInfo(storeId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }


    @ApiOperation(value = "역할과 기능 맵핑 정보 반영", notes = "역할과 기능의 맵핑 정보를 추가/삭제합니다", nickname = Constants.API_ROLE_FUNC + "||5")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "역할 ID", required = true, example = "1", name = "roleId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "기능 ID", required = true, example = "1", name = "funcId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "관리점 ID", required = true, example = "1", name = "storeId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "맵핑 상태", required = true, example = "N", name = "mapYn", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/setFuncRoleMapInfo")
    public ResponseEntity<CommonResponse<Void>> setFuncRoleMapInfo(@ApiIgnore @RequestBody List<ModelMap> reqModel) {

        if(CommonUtil.isNotEmptyList(reqModel)
                && reqModel.get(0).get("storeId") != null){

            Long storeId = authenticationUtil.getTargetStoreId(Long.parseLong(String.valueOf(reqModel.get(0).get("storeId"))));

            roleFuncMgmtService.setFuncRoleMapInfo(reqModel, storeId);
            return responseHandler.ok();
        }else{
            return responseHandler.fail(ResponseCodeEnum.NODATA.getResultMsg());
        }

    }
}


























