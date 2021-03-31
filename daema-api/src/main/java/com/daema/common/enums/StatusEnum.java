package com.daema.common.enums;

public enum StatusEnum {

    FLAG_N(0, "N"),
    FLAG_Y(1, "Y"),

    USER_REG(1, "등록요청"),
    USER_APPROVAL(6, "승인완료"),
    USER_DEL(9, "삭제"),

    //상품, 옵션, 부가서비스
    REG_REQ(1, "등록요청"),
    REG_REQ_APPROVAL(6, "승인"),
    REG_REQ_REJECT(9, "반려"),

    ;

    private int statusCode;
    private String statusMsg;

    StatusEnum(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMsg() {
        return this.statusMsg;
    }
}
