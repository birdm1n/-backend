package com.daema.api.response.enums;

public enum ResponseCodeEnum {
    OK("0000", "OK"),
    NODATA("9000", "조회된 결과가 없습니다"),
    FAIL("9999", "FAIL")
    ;

    private String resultCode;
    private String resultMsg;

    private ResponseCodeEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public String getResultMsg() {
        return this.resultMsg;
    }
}
