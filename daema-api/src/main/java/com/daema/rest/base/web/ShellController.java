package com.daema.rest.base.web;

import com.daema.base.enums.ShellEnum;
import com.daema.base.enums.TypeEnum;
import com.daema.rest.base.service.ExcelDownloadService;
import com.daema.rest.base.service.ShellService;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.consts.PropertiesValue;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/shell" )
public class ShellController {

    private final ShellService shellService;

    /**
     * shell sample
     */
    @GetMapping("/call")
    public CommonResponse fileDownloadSample(){
        try {
            shellService.callShellSample();
            return new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "정상", null);
        } catch (Exception e) {
            return new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "에러", null);
        }
    }

    /**
     * shell call
     */
    @GetMapping("/call/{shellType}")
    public CommonResponse fileDownloadSample(@PathVariable ShellEnum shellType){
        try {
            shellService.callShell(shellType);
            return new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "구전산 기기 매칭 완료.", null);
        } catch (Exception e) {
            return new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "기기매칭 하는 도중 오류가 발생했습니다.", null);
        }
    }


}


























