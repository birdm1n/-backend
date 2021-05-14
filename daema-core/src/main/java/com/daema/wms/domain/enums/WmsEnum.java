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

    public enum StockStatStr {
        I("매장재고"),
        M("이동재고")
        ;
        private final String statusMsg;

        StockStatStr(String statusMsg) {
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

    public enum InStockType {
        IN_STOCK("입고"),

        ;
        private final String statusMsg;

        InStockType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum MoveStockType {
        SELL_MOVE("판매이동"),
        STOCK_MOVE("재고이동"),

        ;
        private final String statusMsg;

        MoveStockType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum OutStockType {
        STOCK_TRNS("재고이관"),
        FAULTY_TRNS("불량이관"),
        SELL_TRNS("판매이관"),

        ;
        private final String statusMsg;

        OutStockType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum StockType {
        UNKNOWN("UNKNOWN"),
        IN_STOCK("입고"),
        SELL_MOVE("판매이동"),
        STOCK_MOVE("재고이동"),
        STOCK_TRNS("재고이관"),
        FAULTY_TRNS("불량이관"),
        SELL_TRNS("판매이관"),
        RETURN_STOCK("반품"),
        ;

        private final String statusMsg;

        StockType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum MovePathType {
        SELL_MOVE("판매이동"),
        STOCK_MOVE("재고이동"),
        STOCK_TRNS("재고이관"),
        FAULTY_TRNS("불량이관"),
        SELL_TRNS("판매이관"),
        ;

        private final String statusMsg;

        MovePathType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum DeliveryType {
        PS("택배"),
        QUICK("퀵"),
        DIRECT("직접전달"),
        ;
        private final String statusMsg;

        DeliveryType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum DeliveryStatus {
        NONE("-"),
        PROGRESS("배송중"),
        COMPL("배송완료")
        ;
        private final String statusMsg;

        DeliveryStatus(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum JudgementStatus {
        WAIT("판정대기"),
        PROGRESS("판정진행중"),
        COMPL("판정완료"),
        REJECT("판정반려"),
        ;
        private final String statusMsg;

        JudgementStatus(String statusMsg) {
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