package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.ProviderMgmtDto;
import com.daema.rest.wms.dto.StockMgmtDto;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.rest.wms.service.ProviderMgmtService;
import com.daema.rest.wms.service.StockInMgmtService;
import com.daema.wms.domain.dto.request.ProviderRequestDto;
import com.daema.wms.domain.dto.request.StockRequestDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "입고 관리 API", tags = "입고 관리 API")
@RestController
@RequestMapping("/v1/api/StockInManagement/StockInMgmt")
public class StockInMgmtController {
    private final StockInMgmtService stockInMgmtService;
    private final ResponseHandler responseHandler;

    public StockInMgmtController(StockInMgmtService stockInMgmtService, ResponseHandler responseHandler) {
        this.stockInMgmtService = stockInMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "입고 조회", notes = "입고 목록을 조회합니다")
    @GetMapping("/getStockInList")
    public ResponseEntity<CommonResponse<ResponseDto<StockMgmtDto>>> getProviderList(StockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(stockInMgmtService.getStockInList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }
}


























