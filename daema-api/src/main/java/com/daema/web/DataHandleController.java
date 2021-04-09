package com.daema.web;

import com.daema.service.DataHandleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dataHandle")
public class DataHandleController {

    private final DataHandleService dataHandleService;

    public DataHandleController(DataHandleService dataHandleService) {
        this.dataHandleService = dataHandleService;
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
}


























