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

    public enum OldMatchState {
        NONE( "보류"),
        OK( "성공"),
        FAIL( "실패"),
        ;
        private final String statusMsg;

        OldMatchState(String statusMsg) {
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

    public enum MoveStockType {
        SELL_MOVE("판매이동"),
        STOCK_MOVE("이동재고"),

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
        FAULTY_TRNS("착하"),
        SELL_TRNS("판매이관"),
        RETURN_TRNS("반품이관"),

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
        STOCK_MOVE("이동재고"),
        STOCK_TRNS("재고이관"),
        FAULTY_TRNS("착하"),
        SELL_TRNS("판매이관"),
        RETURN_TRNS("반품이관"),
        RETURN_STOCK("반품"),

        //요청에 의해 제거 등 수기 처리
        SYSTEM_MGMT_IN_STOCK("시스템관리_입고"),
        SYSTEM_MGMT_SELL_MOVE("시스템관리_판매이동"),
        SYSTEM_MGMT_STOCK_MOVE("시스템관리_이동재고"),
        SYSTEM_MGMT_STOCK_TRNS("시스템관리_재고이관"),
        SYSTEM_MGMT_FAULTY_TRNS("시스템관리_착하"),
        SYSTEM_MGMT_SELL_TRNS("시스템관리_판매이관"),
        SYSTEM_MGMT_RETURN_STOCK("시스템관리_반품"),
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
        STOCK_MOVE("이동재고"),
        STOCK_TRNS("재고이관"),
        FAULTY_TRNS("착하"),
        SELL_TRNS("판매이관"),
        RETURN_TRNS("반품이관"),
        ;

        private final String statusMsg;

        MovePathType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    //재고관리_페이지 구분
    public enum StoreStockPathType {
        STORE_STOCK("재고현황"),
        LONG_TIME_STORE_STOCK("장기재고"),
        FAULTY_STORE_STOCK("불량기기현황"),
        ;

        private final String statusMsg;

        StoreStockPathType(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }

    public enum DeliveryType {
        UNKNOWN("UNKNOWN"),
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
        NONE("-"),
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

    public enum HistoryStatus {
        USE,
        WAIT,
        DEL
        ;
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

    /* OPENING("개통") - 철회 가능, CANCEL("철회") - 철회 완료, COMPL("완료") - 철회불가 */
    public enum OpeningStatus {
        OPENING("개통", "철회가능" ),
        CANCEL("철회", "철회완료"),
        COMPL("완료" , "철회불가")
        ,
        ;

        private final String statusMsg;
        private final String cancelMsg;

        OpeningStatus(String statusMsg, String cancelMsg) {
            this.statusMsg = statusMsg;
            this.cancelMsg = cancelMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
        public String getCancelMsg() {
            return cancelMsg;
        }
    }
    public enum OpeningText {
        NONE("-"),
        OPENING("개통"),
        NOT_OPENING("미개통")
        ;
        private final String statusMsg;

        OpeningText(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }
}