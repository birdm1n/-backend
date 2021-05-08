package com.daema.rest.wms.web;

import com.daema.rest.commgmt.service.GoodsMgmtService;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.rest.wms.service.InStockMgmtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "검색 필터 API", tags = "검색필터 API")
@RestController
@RequestMapping("/v1/api/Wms/Search")
public class WmsSearchController {
    private final InStockMgmtService inStockMgmtService;
    private final GoodsMgmtService goodsMgmtService;
    private final ResponseHandler responseHandler;

    public WmsSearchController(InStockMgmtService inStockMgmtService, GoodsMgmtService goodsMgmtService, ResponseHandler responseHandler) {
        this.inStockMgmtService = inStockMgmtService;
        this.goodsMgmtService = goodsMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "관리점 기기 목록 조회", notes = "관리점이 보유하고 있는 기기목록을 조회")
    @GetMapping("/getDeviceList")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getDeviceList(@ApiParam(value = "통신사 ID") @RequestParam int telecomId,
                                                                                @ApiParam(value = "제조사 ID") @RequestParam int makerId) {
        return responseHandler.getResponseMessageAsRetrieveResult(inStockMgmtService.getDeviceList(telecomId, makerId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "상품 용량 목록 조회", notes = "선택한 상품의 용량 조회")
    @GetMapping("/getCapacityList")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getCapacityList() {
        return responseHandler.getResponseMessageAsRetrieveResult(goodsMgmtService.getCapacityList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "상품 색상 목록 조회", notes = "선택한 상품의 색상 조회")
    @GetMapping("/getColorList/{goodsId}")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getColorList(@ApiParam(value = "상품 ID", required = true) @PathVariable long goodsId) {
        return responseHandler.getResponseMessageAsRetrieveResult(goodsMgmtService.getColorList(goodsId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }



}