package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.service.StoreStockMgmtService;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Api(value = "재고 관리 API", tags = "재고 관리 API")
@RestController
@RequestMapping("/v1/api/StoreStockManagement/StoreStoreStockMgmt")
public class StoreStockMgmtController {

    private final StoreStockMgmtService storeStockMgmtService;

    private final ResponseHandler responseHandler;

    public StoreStockMgmtController(StoreStockMgmtService storeStockMgmtService, ResponseHandler responseHandler) {
        this.storeStockMgmtService = storeStockMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "재고 현황", notes = "재고 목록을 조회합니다")
    @GetMapping("/getStoreStockList")
    public ResponseEntity<CommonResponse<ResponseDto<StoreStockResponseDto>>> getStoreStockList(StoreStockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(storeStockMgmtService.getStoreStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "재고 확인", notes = "재고 확인 처리를 합니다.")
    @PostMapping("/checkStoreStock")
    public ResponseEntity<CommonResponse<Set<Long>>> checkStoreStock(@ApiParam(value = "기기일련번호", required = true) @RequestBody String fullBarcode) {
        storeStockMgmtService.checkStoreStock(fullBarcode);
        return responseHandler.ok();
    }
}