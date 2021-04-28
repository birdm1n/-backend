package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.InStockMgmtDto;
import com.daema.rest.wms.dto.ProviderMgmtDto;
import com.daema.rest.wms.dto.StockMgmtDto;
import com.daema.rest.wms.service.DeviceMgmtService;
import com.daema.rest.wms.service.InStockMgmtService;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.WaitInStockDto;
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
    public ResponseEntity<CommonResponse<ResponseDto<WaitInStockDto>>> getWaitInStockList(InStockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(inStockMgmtService.getWaitInStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

}