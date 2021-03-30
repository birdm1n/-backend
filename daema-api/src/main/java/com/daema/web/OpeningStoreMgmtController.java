package com.daema.web;

import com.daema.dto.OpeningStoreMgmtDto;
import com.daema.dto.OpeningStoreMgmtRequestDto;
import com.daema.dto.OpeningStoreSaleStoreResponseDto;
import com.daema.dto.OpeningStoreUserResponseDto;
import com.daema.dto.common.ResponseDto;
import com.daema.response.enums.ResponseCodeEnum;
import com.daema.response.handler.ResponseHandler;
import com.daema.response.io.CommonResponse;
import com.daema.service.OpeningStoreMgmtService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "개통점 관리 API", tags = "개통점 관리 API")
@RestController
@RequestMapping("/v1/api/BusinessManManagement/OpeningStoreMgmt")
public class OpeningStoreMgmtController {

    private final OpeningStoreMgmtService openingStoreMgmtService;

    private final ResponseHandler responseHandler;

    public OpeningStoreMgmtController(OpeningStoreMgmtService openingStoreMgmtService, ResponseHandler responseHandler) {
        this.openingStoreMgmtService = openingStoreMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "개통점 조회", notes = "개통점 목록을 조회합니다")
    @GetMapping("/getStoreList")
    public ResponseEntity<CommonResponse<ResponseDto<OpeningStoreMgmtDto>>> getOpenStoreList(OpeningStoreMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(openingStoreMgmtService.getOpenStoreList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "개통점 상세 조회", notes = "특정 개통점의 상세 내용을 조회합니다")
    @GetMapping("/getStoreDetail")
    public ResponseEntity<CommonResponse<OpeningStoreMgmtDto>> getOpenStoreDetail(@ApiParam(value = "개통점 ID", required = true, example = "1") @RequestParam long openStoreId
    ,@ApiParam(value = "관리점 ID", required = true, example = "1") @RequestParam long storeId) {
        OpeningStoreMgmtDto openingStoreMgmtDto = openingStoreMgmtService.getOpenStoreDetail(openStoreId, storeId);
        if (openingStoreMgmtDto == null) {
            return responseHandler.fail(ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
        }
        return responseHandler.ok(openingStoreMgmtDto);

    }

    @ApiOperation(value = "개통점 등록", notes = "신규 개통점을 등록합니다")
    @PostMapping("/insertStore")
    public ResponseEntity<CommonResponse<Void>> insertOpenStore(@ApiParam(value = "개통점 정보", required = true) @RequestBody OpeningStoreMgmtDto openingStoreMgmtDto) {
        openingStoreMgmtService.insertOpenStore(openingStoreMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "개통점 수정", notes = "특정 개통점의 내용을 변경합니다")
    @PostMapping("/updateStore")
    public ResponseEntity<CommonResponse<Void>> updateOpenStore(@ApiParam(value = "개통점 정보", required = true) @RequestBody OpeningStoreMgmtDto openingStoreMgmtDto) {
        openingStoreMgmtService.updateOpenStoreInfo(openingStoreMgmtDto);

        return responseHandler.ok();
    }

    @ApiOperation(value = "개통점 삭제", notes = "특정 개통점을 삭제합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "개통점 ID", required = true, example = "1", name = "delOpenStoreId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "관리점 ID", required = true, example = "1", name = "storeId")
    })
    @PostMapping("/deleteStore")
    public ResponseEntity<CommonResponse<Void>> deleteOpenStore(@ApiIgnore @RequestBody ModelMap reqModel) {

        openingStoreMgmtService.deleteOpenStore(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "개통점 사용여부 수정", notes = "특정 개통점의 사용 여부를 변경합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "개통점 ID", required = true, example = "1", name = "openStoreId"),
            @ApiImplicitParam(value = "관리점 ID", required = true, example = "1", name = "storeId"),
            @ApiImplicitParam(value = "소유점 ID", required = true, example = "1", name = "authStoreId"),
            @ApiImplicitParam(value = "사용여부", required = true, name = "useYn")
    })
    @PostMapping("/updateStoreUse")
    public ResponseEntity<CommonResponse<Void>> updateOpenStoreUse(@ApiIgnore @RequestBody ModelMap reqModel) {
        openingStoreMgmtService.updateOpenStoreUse(reqModel);

        return responseHandler.ok();
    }


    @ApiOperation(value = "개통점과 영업점 맵핑 조회", notes = "개통점과 영업점의 맵핑 데이터를 목록으로 조회합니다")
    @GetMapping("/getSaleStoreMapInfo")
    public ResponseEntity<CommonResponse<OpeningStoreSaleStoreResponseDto>> getSaleStoreMapInfo(@ApiParam(value = "관리점 ID", required = true, example = "1") @RequestParam long storeId){
        return responseHandler.getResponseMessageAsRetrieveResult(openingStoreMgmtService.getSaleStoreMapInfo(storeId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }


    @ApiOperation(value = "개통점과 영업점 맵핑 정보 반영", notes = "개통점과 영업점의 맵핑 정보를 추가/삭제합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "개통점 ID", required = true, example = "1", name = "openStoreId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "영업점 ID", required = true, example = "1", name = "saleStoreId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "맵핑 상태", required = true, example = "N", name = "mapYn", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/setSalesStoreMapInfo")
    public ResponseEntity<CommonResponse<Void>> setSalesStoreMapInfo(@ApiIgnore @RequestBody List<ModelMap> reqModel) {

        openingStoreMgmtService.setSalesStoreMapInfo(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "개통점과 사용자 맵핑 조회", notes = "개통점과 사용자의 맵핑 데이터를 목록으로 조회합니다")
    @GetMapping("/getUserMapInfo")
    public ResponseEntity<CommonResponse<OpeningStoreUserResponseDto>> getUserMapInfo(@ApiParam(value = "관리점 ID", required = true, example = "1") @RequestParam long storeId){
        return responseHandler.getResponseMessageAsRetrieveResult(openingStoreMgmtService.getUserMapInfo(storeId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "개통점과 사용자 맵핑 정보 반영", notes = "개통점과 사용자의 맵핑 정보를 추가/삭제합니다")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "개통점 ID", required = true, example = "1", name = "openStoreId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "사용자 ID", required = true, example = "1", name = "userId", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(value = "맵핑 상태", required = true, example = "N", name = "mapYn", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/setUserMapInfo")
    public ResponseEntity<CommonResponse<Void>> setUserMapInfo(@ApiIgnore @RequestBody List<ModelMap> reqModel) {

        openingStoreMgmtService.setUserMapInfo(reqModel);

        return responseHandler.ok();
    }
}


















