package com.daema.rest.wms.web;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.wms.service.InStockMgmtService;
import com.daema.rest.wms.service.MoveStockMgmtService;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "재고이동/이관 API", tags = "재고이동/이관 API")
@RestController
@RequestMapping("/v1/api/DeviceManagement/MoveStockMgmt")
public class MoveStockMgmtController {
    private final MoveStockMgmtService moveStockMgmtService;
    private final ResponseHandler responseHandler;

    public MoveStockMgmtController(MoveStockMgmtService moveStockMgmtService, ResponseHandler responseHandler) {
        this.moveStockMgmtService = moveStockMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "재고이동/이관 목록 조회", notes = "재고이동/이관 목록을 조회합니다")
    @GetMapping("/getMoveAndTrnsList/{movePathType}")
    public ResponseEntity<CommonResponse<ResponseDto<MoveStockResponseDto>>> getMoveAndTrnsList(@ApiParam(value = "조회 타입", required = true) WmsEnum.MovePathType movePathType) {
        return responseHandler.getResponseMessageAsRetrieveResult(moveStockMgmtService.getMoveAndTrnsList(movePathType), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

}