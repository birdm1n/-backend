package com.daema.rest.commgmt.web;

import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.SaleStoreMgmtDto;
import com.daema.rest.commgmt.dto.request.SaleStoreUserWrapperDto;
import com.daema.rest.commgmt.service.SaleStoreMgmtService;
import com.daema.rest.common.Constants;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "영업점 관리 API", tags = "영업점 관리 API")
@RestController
@RequestMapping("/v1/api/BusinessManManagement/SaleStoreMgmt")
public class SaleStoreMgmtController {

    private final SaleStoreMgmtService saleStoreMgmtService;

    private final ResponseHandler responseHandler;

    public SaleStoreMgmtController(SaleStoreMgmtService saleStoreMgmtService, ResponseHandler responseHandler) {
        this.saleStoreMgmtService = saleStoreMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "영업점 조회", notes = "영업점 목록을 조회합니다", nickname = Constants.API_SALE_STORE + "||1")
    @GetMapping("/getStoreList")
    public ResponseEntity<CommonResponse<ResponseDto<SaleStoreMgmtDto>>> getStoreList(ComMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(saleStoreMgmtService.getStoreList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "영업점 등록", notes = "기등록 된 영업점을 관리점 하위 영업점으로 추가합니다", nickname = Constants.API_SALE_STORE + "||2")
    @PostMapping("/insertStoreMap")
    public ResponseEntity<CommonResponse<Void>> insertStoreMap(@ApiParam(value = "영업점 정보", required = true) @RequestBody SaleStoreUserWrapperDto wrapperDto) {
        saleStoreMgmtService.insertStoreMap(wrapperDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "영업점 삭제", notes = "관리점과 영업점과의 맵핑을 제거합니다", nickname = Constants.API_SALE_STORE + "||3")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "영업점 ID", required = true, example = "1", name = "delStoreId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "관리점 ID", required = true, example = "1", name = "parentStoreId")
    })
    @PostMapping("/deleteStore")
    public ResponseEntity<CommonResponse<Void>> deleteStore(@ApiIgnore @RequestBody ModelMap reqModel) {

        saleStoreMgmtService.deleteStore(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "영업점 사용여부 수정", notes = "영업점의 사용 여부를 변경합니다", nickname = Constants.API_SALE_STORE + "||4")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "영업점 ID", required = true, example = "1", name = "storeId"),
            @ApiImplicitParam(value = "관리점 ID", required = true, example = "1", name = "parentStoreId"),
            @ApiImplicitParam(value = "사용여부", required = true, name = "useYn")
    })
    @PostMapping("/updateStoreUse")
    public ResponseEntity<CommonResponse<Void>> updateStoreUse(@ApiIgnore @RequestBody ModelMap reqModel) {

        saleStoreMgmtService.updateStoreUse(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "영업점 가입", notes = "신규 영업점이 가입합니다")
    @PostMapping("/joinStore")
    public ResponseEntity<CommonResponse<Void>> joinStore(@ApiParam(value = "영업점 정보", required = true) @RequestBody SaleStoreUserWrapperDto wrapperDto) {
        try {
            saleStoreMgmtService.insertStoreAndUserAndStoreMap(wrapperDto);
            return responseHandler.ok();
        }catch (Exception e){
            return responseHandler.exception("회원가입을 하는 도중 오류가 발생했습니다.");
        }
    }

    @ApiOperation(value = "영업점 수정", notes = "영업점의 내용을 변경합니다")
    @PostMapping("/updateStore")
    public ResponseEntity<CommonResponse<Void>> updateStore(@ApiParam(value = "영업점 정보", required = true) @RequestBody SaleStoreUserWrapperDto wrapperDto) {
        saleStoreMgmtService.updateStoreInfo(wrapperDto);

        return responseHandler.ok();
    }
}


























