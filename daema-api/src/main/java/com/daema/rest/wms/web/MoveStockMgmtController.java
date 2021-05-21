package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.dto.request.*;
import com.daema.rest.wms.dto.response.SearchMatchResponseDto;
import com.daema.rest.wms.service.MoveStockMgmtService;
import com.daema.wms.domain.dto.request.MoveMgmtRequestDto;
import com.daema.wms.domain.dto.request.MoveStockRequestDto;
import com.daema.wms.domain.dto.response.MoveMgmtResponseDto;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "재고이동/이관 API", tags = "재고이동/이관 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/DeviceManagement/MoveStockMgmt")
public class MoveStockMgmtController {
    private final MoveStockMgmtService moveStockMgmtService;
    private final ResponseHandler responseHandler;


    @ApiOperation(value = "재고이동/이관 목록 조회", notes = "재고이동/이관 목록을 조회합니다")
    @GetMapping("/getMoveAndTrnsList/{movePathType}")
    public ResponseEntity<CommonResponse<ResponseDto<?>>> getMoveAndTrnsList(@ApiParam(value = "조회 타입", required = true) @PathVariable WmsEnum.MovePathType movePathType,
                                                                                                @ApiParam(value = "조회 DTO", required = true) MoveStockRequestDto moveStockRequestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(moveStockMgmtService.getMoveAndTrnsList(movePathType, moveStockRequestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "이관처 선택 리스트 조회", notes = "이관할 관리점 정보를 가져온다.")
    @GetMapping("/getTransStoreList")
    public ResponseEntity<CommonResponse<SearchMatchResponseDto>> getTransStoreList() {
        return responseHandler.getResponseMessageAsRetrieveResult(moveStockMgmtService.getTransStoreList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
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
    public ResponseEntity<CommonResponse<Void>> insertStockMove(@RequestBody StockMoveInsertReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = moveStockMgmtService.insertStockMove(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "재고이관 등록", notes = "재고이관 등록 기능을 수행합니다.")
    @PostMapping("/insertStockTrans")
    public ResponseEntity<CommonResponse<Void>> insertStockTrans(@RequestBody StockTransInsertReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = moveStockMgmtService.insertStockTrans(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "판매이관 등록", notes = "판매이관 등록 기능을 수행합니다.")
    @PostMapping("/insertSellTrans")
    public ResponseEntity<CommonResponse<Void>> insertSellTrans(@RequestBody SellTransInsertReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = moveStockMgmtService.insertSellTrans(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "불량이관 등록", notes = "불량이관 등록 기능을 수행합니다.")
    @PostMapping("/insertFaultyTrans")
    public ResponseEntity<CommonResponse<Void>> insertFaultyTrans(@RequestBody FaultyTransInsertReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = moveStockMgmtService.insertFaultyTrans(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "판매이동 삭제", notes = "판매이동 데이터를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "이동재고 ID", required = true, example = "1", name = "moveStockId", paramType = "query", allowMultiple = true)
    })
    @PostMapping("/deleteMoveStock")
    public ResponseEntity<CommonResponse<Void>> deleteMoveStock(@ApiIgnore @RequestBody ModelMap reqModel) {
        moveStockMgmtService.deleteMoveStock(reqModel);
        return responseHandler.ok();
    }

    @ApiOperation(value = "판매이동 수정", notes = "판매이동 수정 기능을 수행합니다.")
    @PostMapping("/updateSellMove")
    public ResponseEntity<CommonResponse<Void>> updateSellMove(@RequestBody SellMoveUpdateReqDto requestDto) {
        ResponseCodeEnum responseCodeEnum = moveStockMgmtService.updateSellMove(requestDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }

    @ApiOperation(value = "이동현황", notes = "이동현황 목록을 조회합니다")
    @GetMapping("/getMoveMgmtList")
    public ResponseEntity<CommonResponse<ResponseDto<MoveMgmtResponseDto>>> getMoveMgmtList(MoveMgmtRequestDto requestDto ) {
        return responseHandler.getResponseMessageAsRetrieveResult(moveStockMgmtService.getMoveMgmtList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }
}