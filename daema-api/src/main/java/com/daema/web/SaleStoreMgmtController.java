package com.daema.web;

import com.daema.dto.SaleStoreMgmtDto;
import com.daema.dto.SaleStoreMgmtRequestDto;
import com.daema.dto.SaleStoreUserWrapperDto;
import com.daema.dto.common.ResponseDto;
import com.daema.response.enums.ResponseCodeEnum;
import com.daema.response.handler.ResponseHandler;
import com.daema.response.io.CommonResponseMessage;
import com.daema.service.SaleStoreMgmtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "영업점 관리 API")
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
    public ResponseEntity<CommonResponseMessage<ResponseDto<SaleStoreMgmtDto>>> getStoreList(SaleStoreMgmtRequestDto requestDto) {
        try{
            return responseHandler.getResponseMessageAsRetrieveResult(saleStoreMgmtService.getStoreList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
        }catch (Exception e){
            e.printStackTrace();
            return responseHandler.exception(e.getMessage());
        }
    }

    @ApiOperation(value = "영업점 상세 조회", notes = "특정 영업점의 상세 내용을 조회합니다")
    @GetMapping("/getStoreDetail")
    public ResponseEntity<CommonResponseMessage<SaleStoreMgmtDto>> getStoreDetail(@ApiParam(value = "영업점 ID", required = true, example = "1") @RequestParam long storeId) {
        try{
            return responseHandler.ok(saleStoreMgmtService.getStoreDetail(storeId));
        }catch (Exception e){
            e.printStackTrace();
            return responseHandler.exception(e.getMessage());
        }
    }

    @ApiOperation(value = "영업점 등록", notes = "신규 영업점을 등록합니다")
    @PostMapping("/insertStore")
    public ResponseEntity<CommonResponseMessage<Void>> insertStore(@ApiParam(value = "영업점 정보", required = true)
                                                               @RequestBody SaleStoreUserWrapperDto wrapperDto) {
        try {
            return responseHandler.getResponseMessageAsCUD(saleStoreMgmtService.insertStoreAndUserAndStoreMap(wrapperDto));
        }catch (Exception e){
            return responseHandler.exception(e.getMessage());
        }
    }

    @ApiOperation(value = "영업점 수정", notes = "특정 영업점의 내용을 변경합니다")
    @PostMapping("/updateStore")
    public ResponseEntity<CommonResponseMessage<Void>> updateStore(@ApiParam(value = "영업점 정보", required = true) @RequestBody SaleStoreUserWrapperDto wrapperDto) {
        return responseHandler.getResponseMessageAsCUD(saleStoreMgmtService.updateStoreInfo(wrapperDto));
    }

    @ApiOperation(value = "영업점 삭제", notes = "특정 영업점을 삭제합니다")
    @PostMapping("/deleteStore")
    public ResponseEntity<CommonResponseMessage<Void>> deleteStore(@ApiParam(value = "영업점 ID", required = true) @RequestBody String id) {
        return responseHandler.getResponseMessageAsCUD(saleStoreMgmtService.deleteStore(id));
    }

    @ApiOperation(value = "영업점 사용여부 수정", notes = "특정 영업점의 사용 여부를 변경합니다")
    @PostMapping("/updateStoreUse")
    public ResponseEntity<CommonResponseMessage<Void>> updateStoreUse(@ApiParam(value = "영업점 ID", required = true) @RequestBody SaleStoreMgmtDto saleStoreMgmtDto) {
        return responseHandler.getResponseMessageAsCUD(saleStoreMgmtService.updateStoreUse(saleStoreMgmtDto));
    }
}


























