package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.service.InStockMgmtService;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.request.InStockWaitInsertReqDto;
import com.daema.wms.domain.dto.response.InStockWaitDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "입고 관리 API", tags = "입고 관리 API")
@RestController
@RequestMapping("/v1/api/DeviceManagement/InStockMgmt")
public class InStockMgmtController {
    private final InStockMgmtService inStockMgmtService;
    private final ResponseHandler responseHandler;

    public InStockMgmtController(InStockMgmtService inStockMgmtService, ResponseHandler responseHandler) {
        this.inStockMgmtService = inStockMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "입고 대기 목록 조회", notes = "입고 대기 목록을 조회합니다")
    @GetMapping("/getWaitInStockList")
    public ResponseEntity<CommonResponse<ResponseDto<InStockWaitDto>>> getWaitInStockList(InStockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(inStockMgmtService.getWaitInStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "입고 대기 등록", notes = "신규 입고대기 처리를 합니다.")
    @GetMapping("/insertWaitInStock")
    public ResponseEntity<CommonResponse<Void>> insertWaitInStock(@ApiParam(value = "입고 정보", required = true) InStockWaitInsertReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = inStockMgmtService.insertWaitInStock(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

}