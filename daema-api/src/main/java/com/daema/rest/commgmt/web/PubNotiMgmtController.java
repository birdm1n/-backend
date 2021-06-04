package com.daema.rest.commgmt.web;

import com.daema.commgmt.domain.dto.response.PubNotiRawDataListDto;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.PubNotiMgmtDto;
import com.daema.rest.commgmt.dto.response.PubNotiMgmtResponseDto;
import com.daema.rest.commgmt.service.PubNotiMgmtService;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "공시지원금 관리 API", tags = "공시지원금 관리 API")
@RestController
@RequestMapping(value = {"/v1/api/PubNotiManagement/PubNotiMgmt", "/api/PubNotiManagement/PubNotiMgmt" })
public class PubNotiMgmtController {

    private final PubNotiMgmtService pubNotiMgmtService;

    private final ResponseHandler responseHandler;

    public PubNotiMgmtController(PubNotiMgmtService pubNotiMgmtService, ResponseHandler responseHandler) {
        this.pubNotiMgmtService = pubNotiMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "공시지원금 목록 조회", notes = "공시지원금 목록을 조회합니다", nickname = Constants.API_PUB_NOTI + "||1")
    @GetMapping("/getList")
    public ResponseEntity<CommonResponse<PubNotiMgmtResponseDto>> getList(@ApiParam(value = "통신사 ID", required = true, example = "1") @RequestParam Long telecom
            , @ApiParam(value = "통신망 ID", required = true, example = "1") @RequestParam Long network) {
        return responseHandler.getResponseMessageAsRetrieveResult(pubNotiMgmtService.getList(telecom, network), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "공시지원금 등록", notes = "신규 공시지원금을 등록합니다", nickname = Constants.API_PUB_NOTI + "||2")
    @PostMapping("/insertData")
    public ResponseEntity<CommonResponse<Void>> insertData(@ApiParam(value = "공시지원금 정보", required = true) @RequestBody PubNotiMgmtDto pubNotiMgmtDto) {
        pubNotiMgmtService.insertData(pubNotiMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "공시지원금 히스토리 조회", notes = "상품,요금의 공시지원금 히스토리를 조회합니다", nickname = Constants.API_PUB_NOTI + "||3")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "상품 ID", required = true, example = "1", name = "goodsId")
            ,@ApiImplicitParam(value = "요금 ID", required = true, example = "1", name = "chargeId")
    })
    @GetMapping("/getHistoryList")
    public ResponseEntity<CommonResponse<ResponseDto<PubNotiMgmtDto>>> getHistoryList(@ApiParam(value = "요금 ID", required = true, example = "1") @RequestParam long chargeId
            , @ApiParam(value = "상품 ID", required = true, example = "1") @RequestParam long goodsId) {
        return responseHandler.getResponseMessageAsRetrieveResult(pubNotiMgmtService.getHistoryList(chargeId, goodsId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "공시지원금 삭제", notes = "공시지원금을 삭제합니다", nickname = Constants.API_PUB_NOTI + "||4")
    @ApiImplicitParam(value = "공시지원금 ID", required = true, example = "1", name = "pubNotiId")
    @PostMapping("/deleteData")
    public ResponseEntity<CommonResponse<Void>> deleteData(@ApiIgnore @RequestBody ModelMap modelMap) {

        pubNotiMgmtService.deleteData(modelMap);

        return responseHandler.ok();
    }

    @ApiOperation(value = "공시지원금 원천 데이터 목록 조회", notes = "공시지원금 원천 데이터 목록을 조회합니다", nickname = Constants.API_PUB_NOTI + "||5")
    @GetMapping("/getRawDataList")
    public ResponseEntity<CommonResponse<List<PubNotiRawDataListDto>>> getRawDataList() {
        return responseHandler.getResponseMessageAsRetrieveResult(pubNotiMgmtService.getRawDataList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }
}


















