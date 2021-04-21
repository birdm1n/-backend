package com.daema.rest.common.io.response;

import com.daema.rest.common.enums.ResponseCodeEnum;

public class FailResponse extends CommonResponse {

    private static final String RESULT_CODE = ResponseCodeEnum.FAIL.getResultCode();

    public FailResponse(){
        super(RESULT_CODE);
    }

    public FailResponse(String resultMsg){
        super(RESULT_CODE, resultMsg, null);
    }

    public FailResponse(String resultCode, String resultMsg){
        super(resultCode, resultMsg, null);
    }
}