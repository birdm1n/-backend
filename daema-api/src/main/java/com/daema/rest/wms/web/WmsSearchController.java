package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.service.GoodsMgmtService;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.rest.wms.service.DeviceMgmtService;
import com.daema.rest.wms.service.InStockMgmtService;
import com.daema.rest.wms.service.ProviderMgmtService;
import com.daema.rest.wms.service.StockMgmtService;
import com.daema.wms.domain.dto.response.DeviceListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "검색 필터 API", tags = "검색필터 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/v1/api/Wms/Search", "/api/Wms/Search" })
public class WmsSearchController {
    private final InStockMgmtService inStockMgmtService;
    private final GoodsMgmtService goodsMgmtService;
    private final StockMgmtService stockMgmtService;
    private final ProviderMgmtService providerMgmtService;
    private final DeviceMgmtService deviceMgmtService;

    private final ResponseHandler responseHandler;



    @ApiOperation(value = "관리점 기기 목록 조회", notes = "관리점이 보유하고 있는 기기목록을 조회")
    @GetMapping("/getDeviceList")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getDeviceList(@ApiParam(value = "통신사 ID", example = "0") @RequestParam(required = false) Long telecomId,
                                                                                @ApiParam(value = "제조사 ID", example = "0") @RequestParam(required = false) Long makerId) {
        return responseHandler.getResponseMessageAsRetrieveResult(inStockMgmtService.getDeviceList(telecomId, makerId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "상품 Select 목록 조회", notes = "상품 Select 목록을 조회합니다")
    @GetMapping("/getGoodsSelectList/{telecomId}")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getGoodsSelectList(@ApiParam(value = "통신사 ID", example = "0", required = true) @PathVariable Long telecomId) {
        return responseHandler.getResponseMessageAsRetrieveResult(goodsMgmtService.getGoodsSelectList(telecomId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "상품 용량 select 목록 조회", notes = "선택한 상품의 용량 조회")
    @GetMapping("/getGoodsSelectCapacityList/{goodsId}")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getGoodsSelectCapacityList(@ApiParam(value = "상품 ID", example = "0", required = true) @PathVariable long goodsId) {
        return responseHandler.getResponseMessageAsRetrieveResult(goodsMgmtService.getGoodsSelectCapacityList(goodsId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "상품 색상 select 목록 조회", notes = "선택한 상품과 용량에 대한 색상 조회")
    @GetMapping("/getColorList")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getColorList(@ApiParam(value = "상품 ID", example = "0", required = true) @RequestParam long goodsId,
                                                                               @ApiParam(value = "용량", required = true) @RequestParam String capacity) {
        return responseHandler.getResponseMessageAsRetrieveResult(goodsMgmtService.getColorList(goodsId, capacity), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "기기ID로 기기의 현재 보유처 조회", notes = "기기ID로 재고를 조회하여 현재 보유처 정보를 가져온다.")
    @GetMapping("/getDeviceStock")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getDeviceStock(@ApiParam(value = "기기ID", required = true) @RequestParam String selDvcId) {
        return responseHandler.getResponseMessageAsRetrieveResult(stockMgmtService.getDeviceStock(selDvcId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "기기ID로 기기의 공급처 조회", notes = "기기ID로 입고를 조회하여 현재 공급처 정보를 가져온다.")
    @GetMapping("/getDeviceProvInfo")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getDeviceProvInfo(@ApiParam(value = "기기ID", required = true) @RequestParam String selDvcId) {
        return responseHandler.getResponseMessageAsRetrieveResult(providerMgmtService.getDeviceProvInfo(selDvcId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "바코드로 기기 조회", notes = "바코드로 재고를 조회하여 기기 목록을 가져온다.")
    @GetMapping("/getDeviceListFromBarcode")
    public ResponseEntity<CommonResponse<ResponseDto<DeviceListResponseDto>>> getDeviceListFromBarcode(@ApiParam(value = "기기 바코드", required = true) @RequestParam String barcode) {
        return responseHandler.getResponseMessageAsRetrieveResult(deviceMgmtService.getDeviceList(barcode), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }
}