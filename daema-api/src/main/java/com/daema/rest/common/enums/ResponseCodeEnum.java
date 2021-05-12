package com.daema.rest.common.enums;

public enum ResponseCodeEnum {
    OK("0000", "OK"),
    NODATA("9000", "조회된 결과가 없습니다"),
    UNAUTHORIZED("9001", "접근가능한 권한을 가지고 있지 않습니다"),
    UNAUTHORIZED_FUNC("9002", "기능 수행 권한을 가지고 있지 않습니다"),
    NOT_APPROVAL_USER("9003", "관리점 담당자에게 승인되지 않은 사용자 입니다"),
    DELETED_USER("9004", "탈퇴한 사용자 입니다"),
    FAIL("9999", "FAIL"),
    NOT_MATCH_TELECOM("1001", "선택하신 통신사와 기기의 통신사가 상이합니다"),
    DUPL_DATA("1002", "중복된 데이터가 존재합니다"),
    NO_STOCK("1003", "보유처 데이터가 존재하지 않습니다"),
    NO_PROV("1004", "공급처 데이터가 존재하지 않습니다"),
    DUPL_DVC("1005", "동일한 기기가 존재합니다"),
    NO_GOODS("1006", "공통 바코드와 일치하는 상품이 존재하지 않습니다"),
    NO_COLOR("1007", "색상을 선택해주세요"),
    NO_CAPACITY("1007", "용량을 선택해주세요"),
    NO_CAPACITY_COLOR("1007", "용량과 색상이 일치하는 기기가 없습니다")
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
