package com.daema.rest.commgmt.web;

import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.GoodsMgmtDto;
import com.daema.rest.commgmt.dto.GoodsOptionDto;
import com.daema.rest.commgmt.dto.GoodsRegReqDto;
import com.daema.rest.commgmt.service.GoodsMgmtService;
import com.daema.rest.common.Constants;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "상품 관리 API", tags = "상품 관리 API")
@RestController
@RequestMapping("/v1/api/GoodsManagement/GoodsMgmt")
public class GoodsMgmtController {

    private final GoodsMgmtService goodsMgmtService;

    private final ResponseHandler responseHandler;

    public GoodsMgmtController(GoodsMgmtService goodsMgmtService, ResponseHandler responseHandler) {
        this.goodsMgmtService = goodsMgmtService;
        this.responseHandler = responseHandler;
    }

    @ApiOperation(value = "상품 목록 조회", notes = "상품 목록을 조회합니다", nickname = Constants.API_GOODS + "||1")
    @GetMapping("/getList")
    public ResponseEntity<CommonResponse<ResponseDto<GoodsMgmtDto>>> getList(ComMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(goodsMgmtService.getList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "상품 등록", notes = "신규 상품을 등록합니다", nickname = Constants.API_GOODS + "||2")
    @PostMapping("/insertData")
    public ResponseEntity<CommonResponse<Void>> insertData(@ApiParam(value = "상품 정보", required = true) @RequestBody GoodsMgmtDto goodsMgmtDto) {
        goodsMgmtService.insertData(goodsMgmtDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "상품 수정", notes = "상품의 내용을 변경합니다", nickname = Constants.API_GOODS + "||3")
    @PostMapping("/updateData")
    public ResponseEntity<CommonResponse<Void>> updateData(@ApiParam(value = "상품 정보", required = true) @RequestBody GoodsMgmtDto goodsMgmtDto) {
        goodsMgmtService.updateData(goodsMgmtDto);

        return responseHandler.ok();
    }

    @ApiOperation(value = "상품 삭제", notes = "상품을 삭제합니다", nickname = Constants.API_GOODS + "||4")
    @ApiImplicitParam(value = "상품 ID", required = true, example = "1", name = "goodsId", paramType = "query", allowMultiple = true)
    @PostMapping("/deleteData")
    public ResponseEntity<CommonResponse<Void>> deleteData(@ApiIgnore @RequestBody ModelMap reqModel) {

        goodsMgmtService.deleteData(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "상품 사용여부 수정", notes = "상품의 사용 여부를 변경합니다", nickname = Constants.API_GOODS + "||5")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "상품 ID", required = true, example = "1", name = "goodsId"),
            @ApiImplicitParam(value = "사용여부", required = true, name = "useYn")
    })
    @PostMapping("/updateUseYn")
    public ResponseEntity<CommonResponse<Void>> updateUseYn(@ApiIgnore @RequestBody ModelMap reqModel) {
        goodsMgmtService.updateUseYn(reqModel);

        return responseHandler.ok();
    }

    @ApiOperation(value = "상품 옵션 저장", notes = "상품 옵션을 저장합니다", nickname = Constants.API_GOODS + "||6")
    @PostMapping("/saveOptionInfo")
    public ResponseEntity<CommonResponse<Void>> saveOptionInfo(@ApiParam(value = "상품 옵션 정보", required = true) @RequestBody List<GoodsOptionDto> goodsOptionDtos) {
        goodsMgmtService.saveOptionInfo(goodsOptionDtos);
        return responseHandler.ok();
    }

    @ApiOperation(value = "상품 등록 요청 조회", notes = "상품 등록 요청 목록을 조회합니다", nickname = Constants.API_GOODS + "||7")
    @GetMapping("/getRegReqList")
    public ResponseEntity<CommonResponse<ResponseDto<GoodsRegReqDto>>> getRegReqList(ComMgmtRequestDto requestDto) {
        return responseHandler.getResponseMessageAsRetrieveResult(goodsMgmtService.getRegReqList(requestDto), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "상품 등록 요청 승인 및 반려", notes = "상품 등록 요청건에 대해 처리합니다", nickname = Constants.API_GOODS + "||8")
    @PostMapping("/updateReqStatus")
    public ResponseEntity<CommonResponse<Void>> updateReqStatus(@ApiParam(value = "상품 등록 요청 건 처리", required = true) @RequestBody GoodsRegReqDto goodsRegReqDto) {
        goodsMgmtService.updateReqStatus(goodsRegReqDto);
        return responseHandler.ok();
    }

    @ApiOperation(value = "매칭 대상 목록 조회", notes = "매칭 대상 목록을 조회합니다", nickname = Constants.API_GOODS + "||9")
    @GetMapping("/getMatchList")
    public ResponseEntity<CommonResponse<ResponseDto<GoodsMgmtDto>>> getMatchList() {
        return responseHandler.getResponseMessageAsRetrieveResult(goodsMgmtService.getMatchList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    @ApiOperation(value = "매칭 완료 처리", notes = "선택한 항목들을 매칭 완료 처리합니다", nickname = Constants.API_GOODS + "||10")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "상품 ID", required = true, example = "1", name = "groupGoodsId", paramType = "query", allowMultiple = true)
            , @ApiImplicitParam(value = "대표 상품 ID", required = true, example = "1", name = "useGoodsId")
    })
    @PostMapping("/applyMatchStatus")
    public ResponseEntity<CommonResponse<Void>> applyMatchStatus(@ApiIgnore @RequestBody ModelMap reqModel) {

        goodsMgmtService.applyMatchStatus(reqModel);

        return responseHandler.ok();
    }
}


















