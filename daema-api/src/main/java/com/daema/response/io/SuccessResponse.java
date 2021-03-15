package com.daema.response.io;

import com.daema.response.enums.ResponseCodeEnum;

public class SuccessResponse extends CommonResponse {

    private static final String RESULT_CODE = ResponseCodeEnum.OK.getResultCode();

    public SuccessResponse(){
        super(RESULT_CODE);
    }

    public SuccessResponse(Object resultObj){
        super(RESULT_CODE, ResponseCodeEnum.OK.getResultMsg(), resultObj);
    }
}