package com.daema.api.response.io;

import com.daema.api.response.enums.ResponseCodeEnum;

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