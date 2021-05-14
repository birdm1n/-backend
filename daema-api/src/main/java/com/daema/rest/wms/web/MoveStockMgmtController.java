package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.request.SellMoveInsertReqDto;
import com.daema.rest.wms.service.MoveStockMgmtService;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "재고이동/이관 API", tags = "재고이동/이관 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/DeviceManagement/MoveStockMgmt")
public class MoveStockMgmtController {
    private final MoveStockMgmtService moveStockMgmtService;
    private final ResponseHandler responseHandler;


    @ApiOperation(value = "재고이동/이관 목록 조회", notes = "재고이동/이관 목록을 조회합니다")
    @GetMapping("/getMoveAndTrnsList/{movePathType}")
    public ResponseEntity<CommonResponse<ResponseDto<MoveStockResponseDto>>> getMoveAndTrnsList(@ApiParam(value = "조회 타입", required = true) WmsEnum.MovePathType movePathType) {
        return responseHandler.getResponseMessageAsRetrieveResult(moveStockMgmtService.getMoveAndTrnsList(movePathType), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "판매이동 등록", notes = "판매이동 등록 기능을 수행합니다.")
    @PostMapping("/insertSellMove")
    public ResponseEntity<CommonResponse<Void>> insertSellMove(@RequestBody SellMoveInsertReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = moveStockMgmtService.insertSellMove(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "재고이동 등록", notes = "재고 이동 등록 기능을 수행합니다.")
    @PostMapping("/insertStockMove")
    public ResponseEntity<CommonResponse<Void>> insertStockMove(@RequestBody SellMoveInsertReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = moveStockMgmtService.insertStockMove(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

}