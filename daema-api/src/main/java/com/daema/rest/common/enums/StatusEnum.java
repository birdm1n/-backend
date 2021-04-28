package com.daema.rest.common.enums;

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

    // 재고 구분
    STOCK_TYPE_SHOP(1, "매장재고"),
    STOCK_TYPE_MOVE(2, "이동재고"),
    STOCK_TYPE_SELL(3, "판매이동"),
    STOCK_TYPE_STOCK(4, "재고이관"),
    STOCK_TYPE_FAULT(5, "불량이관"),

    // 입고 - 입고상태
    IN_STATUS_WAIT(0, "대기"),
    IN_STATUS_NORMAL(1, "정상"),
    IN_STATUS_OPEN(2, "개봉"),


    // 기기 - 외장상태
    EXTRR_E(0, "-"),
    EXTRR_H(1, "상"),
    EXTRR_M(2, "중"),
    EXTRR_S(3, "하"),
    EXTRR_B(4, "파손"),
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
