package com.daema.response.io;

import com.daema.response.enums.ResponseCodeEnum;

public class FailResponseMessage extends CommonResponseMessage {

    private static final String RESULT_CODE = ResponseCodeEnum.FAIL.getResultCode();

    public FailResponseMessage(){
        super(RESULT_CODE);
    }

    public FailResponseMessage(String resultMsg){
        super(RESULT_CODE, resultMsg, null);
    }

    public FailResponseMessage(String resultCode, String resultMsg){
        super(resultCode, resultMsg, null);
    }
}