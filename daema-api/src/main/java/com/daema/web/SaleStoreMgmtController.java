package com.daema.web;

import com.daema.dto.SaleStoreMgmtDto;
import com.daema.dto.SaleStoreMgmtRequestDto;
import com.daema.dto.SaleStoreUserWrapperDto;
import com.daema.dto.common.ResponseDto;
import com.daema.response.enums.ResponseCodeEnum;
import com.daema.response.handler.ResponseHandler;
import com.daema.response.io.CommonResponse;
import com.daema.service.SaleStoreMgmtService;
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

    @ApiOperation(value = "영업점 조회", notes = "영업점 목록을 조회합니다")
    @GetMapping("/getStoreList")
    public ResponseEntity<CommonResponse<ResponseDto<SaleStoreMgmtDto>>> getStoreList(SaleStoreMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(saleStoreMgmtService.getStoreList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "영업점 상세 조회", notes = "특정 영업점의 상세 내용을 조회합니다")
    @GetMapping("/getStoreDetail")
    public ResponseEntity<CommonResponse<SaleStoreMgmtDto>> getStoreDetail(@ApiParam(value = "영업점 ID", required = true, example = "1") @RequestParam long storeId) {
        SaleStoreMgmtDto saleStoreMgmtDto = saleStoreMgmtService.getStoreDetail(storeId);
        if (saleStoreMgmtDto == null) {
            return responseHandler.fail(ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
        }
        return responseHandler.ok(saleStoreMgmtDto);
    }

    @ApiOperation(value = "영업점 등록", notes = "신규 영업점을 등록합니다")
    @PostMapping("/insertStore")
    public ResponseEntity<CommonResponse<Void>> insertStore(@ApiParam(value = "영업점 정보", required = true) @RequestBody SaleStoreUserWrapperDto wrapperDto) {
        saleStoreMgmtService.insertStoreAndUserAndStoreMap(wrapperDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "영업점 수정", notes = "특정 영업점의 내용을 변경합니다")
    @PostMapping("/updateStore")
    public ResponseEntity<CommonResponse<Void>> updateStore(@ApiParam(value = "영업점 정보", required = true) @RequestBody SaleStoreUserWrapperDto wrapperDto) {
        saleStoreMgmtService.updateStoreInfo(wrapperDto);

        return responseHandler.ok();
    }

    @ApiOperation(value = "영업점 삭제", notes = "관리점과 영업점과의 맵핑을 제거합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "영업점 ID", required = true, example = "1", name = "delStoreId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "관리점 ID", required = true, example = "1", name = "parentStoreId")
    })
    @PostMapping("/deleteStore")
    public ResponseEntity<CommonResponse<Void>> deleteStore(@ApiIgnore @RequestBody ModelMap reqModel) {

        saleStoreMgmtService.deleteStore(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "영업점 사용여부 수정", notes = "특정 영업점의 사용 여부를 변경합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "영업점 ID", required = true, example = "1", name = "delStoreId"),
            @ApiImplicitParam(value = "관리점 ID", required = true, example = "1", name = "parentStoreId"),
            @ApiImplicitParam(value = "사용여부", required = true, name = "useYn")
    })
    @PostMapping("/updateStoreUse")
    public ResponseEntity<CommonResponse<Void>> updateStoreUse(@ApiIgnore @RequestBody ModelMap reqModel) {

        saleStoreMgmtService.updateStoreUse(reqModel);

        return responseHandler.ok();
    }
}


























