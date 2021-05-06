package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.ReturnStockDto;
import com.daema.rest.wms.dto.request.ReturnStockReqDto;
import com.daema.rest.wms.service.ReturnStockMgmtService;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "이동재고반품 API", tags = "이동재고반품 API")
@RestController
@RequestMapping("/v1/api/DeviceManagement/ReturnStockMgmt")
public class ReturnStockMgmtController {
    private final ReturnStockMgmtService returnStockMgmtService;
    private final ResponseHandler responseHandler;

    public ReturnStockMgmtController(ReturnStockMgmtService returnStockMgmtService, ResponseHandler responseHandler) {
        this.returnStockMgmtService = returnStockMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "이동재고반품 목록 조회", notes = "이동재고반품 목록을 조회합니다")
    @GetMapping("/getReturnStockList")
    public ResponseEntity<CommonResponse<ResponseDto<ReturnStockDto>>> getReturnStockList(ReturnStockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(returnStockMgmtService.getReturnStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "이동재고반품 등록", notes = "신규 이동재고반품 처리를 합니다.")
    @PostMapping("/insertReturnStock")
    public ResponseEntity<CommonResponse<List<Long>>> insertReturnStock(@ApiParam(name = "신규 이동재고반품", required = true) @RequestBody List<ReturnStockReqDto> returnStockDtoList) {
        List<Long> fails = returnStockMgmtService.insertReturnStock(returnStockDtoList);

        return responseHandler.ok(fails);
    }
}