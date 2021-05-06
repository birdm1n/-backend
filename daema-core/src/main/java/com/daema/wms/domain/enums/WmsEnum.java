package com.daema.wms.domain.enums;

public enum WmsEnum {
    ;

    public enum DeviceExtrrStatus {
        T( "상"),
        M( "중"),
        B( "하"),
        F( "파손")
        ;
        private final String statusMsg;

        DeviceExtrrStatus(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum InStockStatus {
        NORMAL( "정상"),
        OPEN("개봉")
        ;
        private final String statusMsg;

        InStockStatus(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum DvcStockType {
        IN_STOCK("입고"),
        SELL_MOVE("판매이동"),
        STOCK_MOVE("재고이동"),
        STOCK_TRNS("재고이관"),
        FAULTY_TRNS("불량이관"),
        SELL_TRNS("판매이관"),
        RETURN("반품"),

        ;
        private final String statusMsg;

        DvcStockType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum BarcodeType {
        S("스캐너"),
        K("키보드")
        ;
        private final String statusMsg;

        BarcodeType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }
}