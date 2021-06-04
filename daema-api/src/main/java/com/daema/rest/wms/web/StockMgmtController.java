package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.StockMgmtDto;
import com.daema.rest.wms.dto.response.StockMgmtResponseDto;
import com.daema.rest.wms.service.StockMgmtService;
import com.daema.wms.domain.dto.request.StockRequestDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Api(value = "보유처 관리 API", tags = "보유처 관리 API")
@RestController
@RequestMapping(value = {"/v1/api/StockManagement/StockMgmt", "/api/StockManagement/StockMgmt" })
public class StockMgmtController {

    private final StockMgmtService stockMgmtService;

    private final ResponseHandler responseHandler;

    public StockMgmtController(StockMgmtService stockMgmtService, ResponseHandler responseHandler) {
        this.stockMgmtService = stockMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "보유처 조회", notes = "보유처 목록을 조회합니다")
    @GetMapping("/getStockList")
    public ResponseEntity<CommonResponse<ResponseDto<StockMgmtResponseDto>>> getStockList(StockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(stockMgmtService.getStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "보유처 등록", notes = "신규 보유처를 등록합니다")
    @PostMapping("/insertStock")
    public ResponseEntity<CommonResponse<Void>> insertStockMap(@ApiParam(value = "보유처 정보", required = true) @RequestBody StockMgmtDto stockMgmtDto) {
        stockMgmtService.insertStock(stockMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "보유처 수정", notes = "보유처의 내용을 변경합니다")
    @PostMapping("/updateStock")
    public ResponseEntity<CommonResponse<Void>> updateStock(@ApiParam(value = "보유처 정보", required = true) @RequestBody StockMgmtDto stockMgmtDto) {
        stockMgmtService.updateStock(stockMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "보유처 삭제", notes = "보유처을 삭제합니다")
    @PostMapping("/deleteStock")
    public ResponseEntity<CommonResponse<Void>> deleteStock(@ApiParam(value = "보유처ID", required = true) @RequestBody ModelMap modelMap) {
        stockMgmtService.deleteStock(Long.parseLong(String.valueOf(modelMap.get("stockId"))));

        return responseHandler.ok();
    }

    @ApiOperation(value = "보유처 선택 리스트", notes = "보유처의 선택 리스트를 조회합니다")
    @GetMapping("/selectStockList/{telecom}")
    public ResponseEntity<CommonResponse<SelectStockDto>> selectStockList(@ApiParam(value = "통신사 번호", example = "0", required = true) @PathVariable(value = "telecom") Long telecom ) {
        return responseHandler.getResponseMessageAsRetrieveResult(stockMgmtService.selectStockList(telecom), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "자신의 보유처를 제외한 이동할 보유처 리스트", notes = "자신의 보유처를 제외한 이동할 보유처 리스트를 조회합니다")
    @GetMapping("/innerStockList")
    public ResponseEntity<CommonResponse<SelectStockDto>> innerStockList() {
        return responseHandler.getResponseMessageAsRetrieveResult(stockMgmtService.innerStockList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

}