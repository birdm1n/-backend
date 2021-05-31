package com.daema.rest.base.web;

import com.daema.base.enums.ShellEnum;
import com.daema.rest.base.service.ShellService;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.io.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/shell" )
public class ShellController {

    private final ShellService shellService;

    /**
     * shell sample
     */
//    @GetMapping("/call")
//    public CommonResponse fileDownloadSample(){
//        try {
//            shellService.callShellSample();
//            return new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "정상", null);
//        } catch (Exception e) {
//            return new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "에러", null);
//        }
//    }

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


























