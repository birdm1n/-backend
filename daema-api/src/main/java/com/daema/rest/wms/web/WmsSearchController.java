package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.rest.wms.service.InStockMgmtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "검색 필터 API", tags = "검색필터 API")
@RestController
@RequestMapping("/v1/api/Wms/Search")
public class WmsSearchController {
    private final InStockMgmtService inStockMgmtService;
    private final ResponseHandler responseHandler;

    public WmsSearchController(InStockMgmtService inStockMgmtService, ResponseHandler responseHandler) {
        this.inStockMgmtService = inStockMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "보유처 제조사 목록 조회", notes = "보유처가 보유하고 있는 기기의 제조사 목록을 조회")
    @GetMapping("/getMakerList/{telecom}/{stockId}")
    public ResponseEntity<CommonResponse<ResponseDto<SearchMatchResponseDto>>> getMakerList(@ApiParam(value = "통신사 ID", required = true) @PathVariable int telecom,
                                                                                            @ApiParam(value = "보유처 ID") @PathVariable long stockId) {
        return responseHandler.getResponseMessageAsRetrieveResult(inStockMgmtService.getMakerList(telecom, stockId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }



}