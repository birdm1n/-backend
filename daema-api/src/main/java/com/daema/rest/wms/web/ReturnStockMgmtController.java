package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.response.DeviceResponseDto;
import com.daema.rest.wms.service.DeviceMgmtService;
import com.daema.rest.wms.service.ReturnStockMgmtService;
import com.daema.wms.domain.dto.request.ReturnStockReqDto;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import com.daema.wms.domain.dto.response.ReturnStockResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Set;

@Api(value = "이동재고반품 API", tags = "이동재고반품 API")
@RestController
@RequestMapping("/v1/api/DeviceManagement/ReturnStockMgmt")
public class ReturnStockMgmtController {
    private final ReturnStockMgmtService returnStockMgmtService;
    private final DeviceMgmtService deviceMgmtService;
    private final ResponseHandler responseHandler;

    public ReturnStockMgmtController(ReturnStockMgmtService returnStockMgmtService, DeviceMgmtService deviceMgmtService, ResponseHandler responseHandler) {
        this.returnStockMgmtService = returnStockMgmtService;
        this.deviceMgmtService = deviceMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "이동재고반품 목록 조회", notes = "이동재고반품 목록을 조회합니다")
    @GetMapping("/getReturnStockList")
    public ResponseEntity<CommonResponse<ResponseDto<ReturnStockResponseDto>>> getReturnStockList(ReturnStockRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(returnStockMgmtService.getReturnStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "이동재고반품 등록", notes = "신규 이동재고반품 처리를 합니다.")
    @PostMapping("/insertReturnStock")
    public ResponseEntity<CommonResponse<Set<Long>>> insertReturnStock(@ApiParam(value = "신규 이동재고반품", required = true) @RequestBody List<ReturnStockReqDto> returnStockDtoList) {
        Set<Long> fails = returnStockMgmtService.insertReturnStock(returnStockDtoList);

        return responseHandler.ok(fails);
    }

    @ApiOperation(value = "기기정보 조회", notes = "기기고유번호로 기기정보를 조회합니다")
    @GetMapping("/getDeviceInfo")
    public ResponseEntity<CommonResponse<ResponseDto<DeviceResponseDto>>> getDeviceInfo(@ApiParam(value = "기기고유번호", required = true) @RequestParam String fullBarcode) {
        return responseHandler.getResponseMessageAsRetrieveResult(deviceMgmtService.getDeviceInfoFromFullBarcode(fullBarcode), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "이동재고반품 등록 엑셀 업로드", notes = "엑셀 업로드로 신규 이동재고반품 처리를 합니다.", produces = "multipart/form-data")
    @PostMapping("/insertReturnStockExcel")
    public ResponseEntity<CommonResponse<Set<String>>> insertReturnStockExcel(@ApiParam(value = "엑셀파일", required = true, name = "excelFile") @RequestPart MultipartFile excelFile, @ApiIgnore MultipartHttpServletRequest mRequest) {
        Set<String> fails = returnStockMgmtService.insertReturnStockExcel(mRequest);
        return responseHandler.ok(fails);
    }
}