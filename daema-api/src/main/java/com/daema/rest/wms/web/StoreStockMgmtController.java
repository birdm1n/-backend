package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.DeviceJudgeDto;
import com.daema.rest.wms.dto.MoveStockAlarmDto;
import com.daema.rest.wms.service.StoreStockMgmtService;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.dto.response.StoreStockCheckListDto;
import com.daema.wms.domain.dto.response.StoreStockResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "재고 관리 API", tags = "재고 관리 API")
@RestController
@RequestMapping("/v1/api/StoreStockManagement/StoreStockMgmt")
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
    public ResponseEntity<CommonResponse<Void>> checkStoreStock(@ApiParam(value = "기기일련번호", required = true) @RequestBody ModelMap paramMap) {
        storeStockMgmtService.checkStoreStock(String.valueOf(paramMap.get("fullBarcode")));
        return responseHandler.ok();
    }

    @ApiOperation(value = "재고 확인 이력", notes = "재고 확인한 이력을 조회합니다")
    @GetMapping("/getStoreStockCheckHistory")
    public ResponseEntity<CommonResponse<List<StoreStockCheckListDto>>> getStoreStockCheckHistory(@ApiParam(value = "재고 ID", required = true, example = "1") @RequestParam Long storeStockId) {
        return responseHandler.getResponseMessageAsRetrieveResult(storeStockMgmtService.getStoreStockCheckHistory(storeStockId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "장기재고 현황", notes = "장기재고 목록을 조회합니다")
    @GetMapping("/getLongTimeStoreStockList")
    public ResponseEntity<CommonResponse<ResponseDto<StoreStockResponseDto>>> getLongTimeStoreStockList(StoreStockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(storeStockMgmtService.getLongTimeStoreStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "장기 재고 알람 정보 조회", notes = "장기 재고 알람 값을 조회 합니다.")
    @PostMapping("/getLongTimeStoreStockAlarm")
    public ResponseEntity<CommonResponse<ResponseDto<MoveStockAlarmDto>>> getLongTimeStoreStockAlarm() {
        return responseHandler.getResponseMessageAsRetrieveResult(storeStockMgmtService.getLongTimeStoreStockAlarm(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "장기 재고 알람 설정", notes = "장기 재고 알람 값을 설정 합니다.")
    @PostMapping("/setLongTimeStoreStockAlarm")
    public ResponseEntity<CommonResponse<Void>> setLongTimeStoreStockAlarm(@RequestBody MoveStockAlarmDto requestDto) {
        storeStockMgmtService.cuMoveStockAlarm(requestDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "불량기기 현황", notes = "불량기기 목록을 조회합니다")
    @GetMapping("/getFaultyStoreStockList")
    public ResponseEntity<CommonResponse<ResponseDto<StoreStockResponseDto>>> getFaultyStoreStockList(StoreStockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(storeStockMgmtService.getFaultyStoreStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "판정 상태 변경", notes = "판정 상태 값을 변경 합니다.")
    @PostMapping("/updateJudgementStatus")
    public ResponseEntity<CommonResponse<Void>> updateJudgementStatus(@RequestBody DeviceJudgeDto requestDto) {
        storeStockMgmtService.updateJudgementStatus(requestDto);
        return responseHandler.ok();
    }
}






















