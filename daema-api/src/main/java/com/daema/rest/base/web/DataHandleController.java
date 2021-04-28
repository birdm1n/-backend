package com.daema.rest.base.web;

import com.daema.rest.base.dto.RetrieveInitDataResponseDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.base.service.DataHandleService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dataHandle")
public class DataHandleController {

    private final DataHandleService dataHandleService;

    private final ResponseHandler responseHandler;

    public DataHandleController(DataHandleService dataHandleService, ResponseHandler responseHandler) {
        this.dataHandleService = dataHandleService;
        this.responseHandler = responseHandler;
    }

    /**
     * ApiOperation 의 nickname 속성을 조회해서 func_mgmt 테이블 데이터 관리
     */
    @PostMapping("/extractApiFunc")
    public void extractApiFunc() {
        dataHandleService.extractApiFunc();
    }

    /**
     * 공시지원금 스마트 초이스 데이터를 조회하여 goods 테이블 데이터 관리
     */
    @PostMapping("/migrationSmartChoiceData")
    public void migrationSmartChoiceData() {
        dataHandleService.migrationSmartChoiceData();
    }

    /**
     * 코드 데이터 및 관리 데이터 응답
     */
    @PostMapping("/retrieveInitData")
    public ResponseEntity<CommonResponse<RetrieveInitDataResponseDto>> retrieveInitData(@RequestBody ModelMap reqModel) {
        return responseHandler.getResponseMessageAsRetrieveResult(dataHandleService.retrieveInitData(reqModel), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    /**
     * 데이터 확인용
     */
    @PostMapping("/existsData")
    public ResponseEntity<CommonResponse<Object>> existsData(@RequestBody ModelMap reqModel, HttpServletRequest request) {
        return responseHandler.getResponseMessageAsRetrieveResult(dataHandleService.existsData(reqModel, request), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
    }

    /**
     * 가입 URL 생성
     */
    @PostMapping("/generatorJoinPath")
    public ResponseEntity<CommonResponse<String>> generatorJoinPath(@RequestBody ModelMap reqModel) {
        return responseHandler.ok(dataHandleService.generatorJoinPath(reqModel));
    }
}


























