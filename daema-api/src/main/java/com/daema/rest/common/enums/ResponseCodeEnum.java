package com.daema.rest.common.enums;

public enum ResponseCodeEnum {
    OK("0000", "OK"),
    NODATA("9000", "조회된 결과가 없습니다"),
    UNAUTHORIZED("9001", "접근가능한 권한을 가지고 있지 않습니다"),
    UNAUTHORIZED_FUNC("9002", "기능 수행 권한을 가지고 있지 않습니다"),
    NOT_APPROVAL_USER("9003", "관리점 담당자에게 승인되지 않은 사용자 입니다"),
    DELETED_USER("9004", "탈퇴한 사용자 입니다"),
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
