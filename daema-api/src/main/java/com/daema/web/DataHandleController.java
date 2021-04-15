package com.daema.web;

import com.daema.dto.RetrieveInitDataResponseDto;
import com.daema.response.enums.ResponseCodeEnum;
import com.daema.response.handler.ResponseHandler;
import com.daema.response.io.CommonResponse;
import com.daema.service.DataHandleService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/extractApiFunc")
    public void extractApiFunc() {
        dataHandleService.extractApiFunc();
    }

    /**
     * 공시지원금 스마트 초이스 데이터를 조회하여 goods 테이블 데이터 관리
     */
    @GetMapping("/migrationSmartChoiceData")
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
}


























