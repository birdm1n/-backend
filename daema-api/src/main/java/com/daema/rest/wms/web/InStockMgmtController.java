package com.daema.rest.wms.web;

import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.service.InStockMgmtService;
import com.daema.wms.domain.InStock;
import com.daema.wms.domain.dto.request.InStockWaitInsertReqDto;
import com.daema.wms.domain.dto.response.InStockWaitResponseDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "입고/입고현황 API", tags = "입고/입고현황 API")
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
    @GetMapping("/getWaitInStockList/{inStockStatus}")
    public ResponseEntity<CommonResponse<InStockWaitResponseDto>> getWaitInStockList(@ApiParam(name = "입고상태", required = true) @PathVariable InStock.StockStatus inStockStatus ) {
        return responseHandler.getResponseMessageAsRetrieveResult(inStockMgmtService.getWaitInStockList(inStockStatus), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "입고 대기 등록", notes = "신규 입고대기 처리를 합니다.")
    @PostMapping("/insertWaitInStock")
    public ResponseEntity<CommonResponse<Void>> insertWaitInStock(@RequestBody InStockWaitInsertReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = inStockMgmtService.insertWaitInStock(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }
    @ApiOperation(value = "입고대기 삭제", notes = "입고대기 데이터를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "대기 ID", required = true, example = "1", name = "waitId", paramType = "query", allowMultiple = true)
    })
    @DeleteMapping("/deleteWaitInStock")
    public ResponseEntity<CommonResponse<Void>> deleteWaitInStock(@ApiIgnore @RequestBody ModelMap reqModel) {
        inStockMgmtService.deleteWaitInStock(reqModel);
        return responseHandler.ok();
    }

}