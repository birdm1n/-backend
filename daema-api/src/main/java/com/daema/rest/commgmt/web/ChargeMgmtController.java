package com.daema.rest.commgmt.web;

import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.ChargeMgmtDto;
import com.daema.rest.commgmt.dto.ChargeRegReqDto;
import com.daema.rest.commgmt.service.ChargeMgmtService;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "요금 관리 API", tags = "요금 관리 API")
@RestController
@RequestMapping(value = {"/v1/api/ChargeManagement/ChargeMgmt", "/api/ChargeManagement/ChargeMgmt" })
public class ChargeMgmtController {

    private final ChargeMgmtService chargeMgmtService;

    private final ResponseHandler responseHandler;

    public ChargeMgmtController(ChargeMgmtService chargeMgmtService, ResponseHandler responseHandler) {
        this.chargeMgmtService = chargeMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "요금 목록 조회", notes = "요금 목록을 조회합니다", nickname = Constants.API_CHARGE + "||1")
    @GetMapping("/getList")
    public ResponseEntity<CommonResponse<ResponseDto<ChargeMgmtDto>>> getList(ComMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(chargeMgmtService.getList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "요금 등록", notes = "신규 요금을 등록합니다", nickname = Constants.API_CHARGE + "||2")
    @PostMapping("/insertData")
    public ResponseEntity<CommonResponse<Void>> insertData(@ApiParam(value = "요금 정보", required = true) @RequestBody ChargeMgmtDto chargeMgmtDto) {
        chargeMgmtService.insertData(chargeMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "요금 수정", notes = "요금의 내용을 변경합니다", nickname = Constants.API_CHARGE + "||3")
    @PostMapping("/updateData")
    public ResponseEntity<CommonResponse<Void>> updateData(@ApiParam(value = "요금 정보", required = true) @RequestBody ChargeMgmtDto chargeMgmtDto) {
        chargeMgmtService.updateData(chargeMgmtDto);

        return responseHandler.ok();
    }

    @ApiOperation(value = "요금 삭제", notes = "요금을 삭제합니다", nickname = Constants.API_CHARGE + "||4")
    @ApiImplicitParam(value = "요금 ID", required = true, example = "1", name = "chargeId", paramType = "query", allowMultiple = true)
    @PostMapping("/deleteData")
    public ResponseEntity<CommonResponse<Void>> deleteData(@ApiIgnore @RequestBody ModelMap reqModel) {

        chargeMgmtService.deleteData(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "요금 사용여부 수정", notes = "요금의 사용 여부를 변경합니다", nickname = Constants.API_CHARGE + "||5")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "요금 ID", required = true, example = "1", name = "chargeId"),
            @ApiImplicitParam(value = "사용여부", required = true, name = "useYn")
    })
    @PostMapping("/updateUseYn")
    public ResponseEntity<CommonResponse<Void>> updateUseYn(@ApiIgnore @RequestBody ModelMap reqModel) {
        chargeMgmtService.updateUseYn(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "요금 등록 요청 조회", notes = "요금 등록 요청 목록을 조회합니다", nickname = Constants.API_CHARGE + "||6")
    @GetMapping("/getRegReqList")
    public ResponseEntity<CommonResponse<ResponseDto<ChargeRegReqDto>>> getRegReqList(ComMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(chargeMgmtService.getRegReqList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "요금 등록 요청 승인 및 반려", notes = "요금 등록 요청건에 대해 처리합니다", nickname = Constants.API_CHARGE + "||7")
    @PostMapping("/updateReqStatus")
    public ResponseEntity<CommonResponse<Void>> updateReqStatus(@ApiParam(value = "요금 등록 요청 건 처리", required = true) @RequestBody ChargeRegReqDto chargeRegReqDto) {
        chargeMgmtService.updateReqStatus(chargeRegReqDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "매칭 대상 목록 조회", notes = "매칭 대상 목록을 조회합니다", nickname = Constants.API_CHARGE + "||8")
    @GetMapping("/getMatchList")
    public ResponseEntity<CommonResponse<ResponseDto<ChargeMgmtDto>>> getMatchList(ComMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(chargeMgmtService.getMatchList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "매칭 완료 처리", notes = "선택한 항목들을 매칭 완료 처리합니다", nickname = Constants.API_CHARGE + "||9")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "요금 ID", required = true, example = "1", name = "groupChargeId", paramType = "query", allowMultiple = true)
            , @ApiImplicitParam(value = "대표 요금 ID", required = true, example = "1", name = "useChargeId")
    })
    @PostMapping("/applyMatchStatus")
    public ResponseEntity<CommonResponse<Void>> applyMatchStatus(@ApiIgnore @RequestBody ModelMap reqModel) {

        chargeMgmtService.applyMatchStatus(reqModel);

        return responseHandler.ok();
    }
}


















