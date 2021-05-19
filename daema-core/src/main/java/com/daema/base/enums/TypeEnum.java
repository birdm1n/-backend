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


    //재고관리_페이지 구분
    public enum StoreStockPageType {
        STORE_STOCK("storeStock"),
        LONG_TIME_STORE_STOCK("longTimeStoreStock"),
        FAULTY_STORE_STOCK("faultyStoreStock"),
        ;

        private final String statusMsg;

        StoreStockPageType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }
}
