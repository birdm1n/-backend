package com.daema.rest.common.enums;

public enum TypeEnum {

    //창고 타입 구분
    STOCK_TYPE_O("O", "OpenStore"),
    STOCK_TYPE_S("S", "SaleStore"),
    STOCK_TYPE_I("I", "Inner"),

    ;

    private String statusCode;
    private String statusMsg;

    TypeEnum(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMsg() {
        return this.statusMsg;
    }
}
