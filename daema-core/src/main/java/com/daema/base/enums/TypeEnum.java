package com.daema.base.enums;

public enum TypeEnum {

    //파일 타입 구분
    XLSX("XLSX", "xlsx"),
    XLS("XLS", "xls"),

    //창고 타입 구분
    STOCK_TYPE_O("O", "OpenStore"),
    STOCK_TYPE_S("S", "SaleStore"),
    STOCK_TYPE_I("I", "Inner"),

    //가입 링크 구분
    JOIN_STORE("S", "STORE"),
    JOIN_USER("U", "USER"),

    //정렬 구분
    DESC("DESC", "DESC"),
    ASC("ASC", "ASC"),
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

    public enum AddSvcType {
        NORMAL("일반"),
        INSURANCE("보험"),
        ;

        private final String statusMsg;

        AddSvcType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }
}
