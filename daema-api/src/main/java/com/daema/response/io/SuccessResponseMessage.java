package com.daema.response.io;

import com.daema.response.enums.ResponseCodeEnum;

public class SuccessResponseMessage extends CommonResponseMessage {

    private static final String RESULT_CODE = ResponseCodeEnum.OK.getResultCode();

    public SuccessResponseMessage(){
        super(RESULT_CODE);
    }

    public SuccessResponseMessage(Object resultObj){
        super(RESULT_CODE, ResponseCodeEnum.OK.getResultMsg(), resultObj);
    }
}