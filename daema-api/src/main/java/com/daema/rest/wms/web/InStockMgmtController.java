package com.daema.rest.wms.web;

import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.core.wms.dto.request.InStockInsertReqDto;
import com.daema.core.wms.dto.request.InStockUpdateReqDto;
import com.daema.core.wms.dto.request.InStockWaitInsertExcelReqDto;
import com.daema.core.wms.dto.request.InStockWaitInsertReqDto;
import com.daema.rest.wms.service.InStockMgmtService;
import com.daema.core.wms.dto.request.InStockRequestDto;
import com.daema.core.wms.dto.response.InStockResponseDto;
import com.daema.core.wms.dto.response.InStockWaitResponseDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Api(value = "입고/입고현황 API", tags = "입고/입고현황 API")
@RestController
@RequestMapping(value = {"/v1/api/DeviceManagement/InStockMgmt", "/api/DeviceManagement/InStockMgmt" })
public class InStockMgmtController {
    private final InStockMgmtService inStockMgmtService;
    private final ResponseHandler responseHandler;

    public InStockMgmtController(InStockMgmtService inStockMgmtService, ResponseHandler responseHandler) {
        this.inStockMgmtService = inStockMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "입고 대기 목록 조회", notes = "입고 대기 목록을 조회합니다")
    @GetMapping("/getWaitInStockList/{inStockStatus}")
    public ResponseEntity<CommonResponse<InStockWaitResponseDto>> getWaitInStockList(@ApiParam(value = "입고상태", required = true) @PathVariable WmsEnum.InStockStatus inStockStatus ) {
        return responseHandler.getResponseMessageAsRetrieveResult(inStockMgmtService.getWaitInStockList(inStockStatus), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "입고 대기 등록", notes = "신규 입고대기 처리를 합니다.")
    @PostMapping("/insertWaitInStock")
    public ResponseEntity<CommonResponse<Void>> insertWaitInStock(@Valid @ApiParam(value = "신규입고대기", required = true) @RequestBody InStockWaitInsertReqDto requestDto) {
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
    @PostMapping("/deleteWaitInStock")
    public ResponseEntity<CommonResponse<Void>> deleteWaitInStock(@ApiIgnore @RequestBody ModelMap reqModel) {
        inStockMgmtService.deleteWaitInStock(reqModel);
        return responseHandler.ok();
    }

    @ApiOperation(value = "입고 목록 조회", notes = "입고 목록을 조회합니다")
    @GetMapping("/getInStockList")
    public ResponseEntity<CommonResponse<ResponseDto<InStockResponseDto>>> getInStockList(@ApiParam(value = "입고상태", required = true) InStockRequestDto requestDto ) {
        return responseHandler.getResponseMessageAsRetrieveResult(inStockMgmtService.getInStockList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "입고 등록", notes = "신규 입고리스트를 처리를 합니다.")
    @PostMapping("/insertInStock")
    public ResponseEntity<CommonResponse<Void>> insertInStock(@ApiParam(value = "신규입고", required = true) @RequestBody List<InStockInsertReqDto> requestDto) {
        ResponseCodeEnum responseCodeEnum = inStockMgmtService.insertInStock(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "입고 목록 수정", notes = "입고 목록을 수정합니다")
    @PostMapping("/updateInStock")
    public ResponseEntity<CommonResponse<Void>> updateInStock(@RequestBody InStockUpdateReqDto requestDto ) {
        inStockMgmtService.updateInStock(requestDto);
        return responseHandler.ok();
    }

    // request
    @ApiOperation(value = "입고대기 등록 엑셀 업로드", notes = "엑셀 업로드로 신규 입고대기 처리를 합니다.", produces = "multipart/form-data")
    @PostMapping("/insertInStockWaitExcel")
    public ResponseEntity<CommonResponse<Set<String>>> insertInStockWaitExcel(@ApiParam(value = "엑셀파일", required = true, name = "excelFile") @RequestPart MultipartFile excelFile,
                                                                              @ApiParam(value = "입고대기 데이터 ", required = true) InStockWaitInsertExcelReqDto requestDto,
                                                                              @ApiIgnore MultipartHttpServletRequest mRequest) {
        Set<String> fails = inStockMgmtService.insertInStockWaitExcel(requestDto, mRequest);
        return responseHandler.ok(fails);
    }


}