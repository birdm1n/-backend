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

    public enum StockStatus {
        NORMAL( "정상"),
        OPEN("개봉")
        ;
        private final String statusMsg;

        StockStatus(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }
}