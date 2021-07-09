package com.daema.core.sms.domain.enums;

public enum SmsEnum {
    ;

    public enum TaskState{

        //LogisState

        DLVR_REQ("배송요청(상담/개통)"),
        DLVR_PEND("배송보류(재고없음)"),
        DLVR_READY("배송준비"),
        DLVR_BFR_CANCEL_REQ("배송전취소요청"),
        DLVR_ING("배송중"),
        DLVR_COMPL("배송완료"),
        RETURN_REQ("반품요청"),
        RETURN_COMPL("반품완료"),

        //OpeningState
        OPENING_REQ_PEND_CONSULT("개통요청보류(상담)"),
        OPENING_REQ_CONSULT("개통요청(상담)"),
        OPENING_ING("개통중"),
        OPENING_CANCEL("개통취소"),
        OPENING_COMPL("개통완료"),
        OPENING_PEND("개통보류"),
        RTRCT_REQ("철회요청(상담)"),
        RTRCT_COMPL("철회완료"),
        USIM_CHANGE_REQ_CONSULT("유심변경요청(상담)"),
        USIM_CHANGE_COMPL("유심변경완료"),
        EXCHANGE_REQ_CONSULT("교퓸요청(상담)"),
        EXCHANGE_COMPL("교퓸완료"),
        BFR_AGREE_ING("사전동의진행중"),
        BFR_AGREE_COMPL("사전동의완료"),

        //ConsultState
        RCPT_ING("접수중"),
        RCPT_CANCEL("접수취소"),
        RCPT_COMPL("접수완료"),
        OPENING_REQ("개통요청"),
        OPENING_REQ_PEND("개통요청보류"),
        USIM_CHANGE_REQ("유심변경요청"),
        EXCHANGE_REQ("교퓸요청");


        private final String statusMsg;

        TaskState(String statusMsg) {
            this.statusMsg = statusMsg;
        }

        public String getStatusMsg() {
            return statusMsg;
        }
    }



      /*  public enum LogisState {

            DLVR_REQ("배송요청(상담/개통)"),
            DLVR_PEND("배송보류(재고없음)"),
            DLVR_READY("배송준비"),
            DLVR_BFR_CANCEL_REQ("배송전취소요청"),
            DLVR_ING("배송중"),
            DLVR_COMPL("배송완료"),
            RETURN_REQ("반품요청"),
            RETURN_COMPL("반품완료");


            private final String statusMsg;

            LogisState(String statusMsg) {
                this.statusMsg = statusMsg;
            }

            public String getStatusMsg() {
                return statusMsg;
            }
        }*/
/*
        public enum OpeningState {

            OPENING_REQ_PEND("개통요청보류(상담)"),
            OPENING_REQ("개통요청(상담)"),
            OPENING_ING("개통중"),
            OPENING_CANCEL("개통취소"),
            OPENING_COMPL("개통완료"),
            OPENING_PEND("개통보류"),
            RTRCT_REQ("철회요청(상담)"),
            RTRCT_COMPL("철회완료"),
            USIM_CHANGE_REQ("유심변경요청(상담)"),
            USIM_CHANGE_COMPL("유심변경완료"),
            EXCHANGE_REQ("교퓸요청(상담)"),
            EXCHANGE_COMPL("교퓸완료"),
            BFR_AGREE_ING("사전동의진행중"),
            BFR_AGREE_COMPL("사전동의완료");


            private final String statusMsg;

            OpeningState(String statusMsg) {
                this.statusMsg = statusMsg;
            }

            public String getStatusMsg() {
                return statusMsg;
            }
        }

        public enum ConsultState {
            RCPT_ING("접수중"),
            RCPT_CANCEL("접수취소"),
            RCPT_COMPL("접수완료"),
            OPENING_REQ("개통요청"),
            OPENING_REQ_PEND("개통요청보류"),
            USIM_CHANGE_REQ("유심변경요청"),
            EXCHANGE_REQ("교퓸요청");


            private final String statusMsg;

            ConsultState(String statusMsg) {
                this.statusMsg = statusMsg;
            }

            public String getStatusMsg() {
                return statusMsg;
            }
        }*/

    public enum InstallmentsPeriod{

        CASH("현금개통"),
        SIX("6개월"),
        YEAR("12개월"),
        EIGHTEEN("18개월"),
        YEAR2("24개월"),
        THIERTY("30개월"),
        YEAR3("36개월"),
        YEAR4("48개월");

        private final String statusMsg;

        InstallmentsPeriod(String statusMsg){
            this.statusMsg = statusMsg;
        }

        public String getStatusMsg() {
            return statusMsg;
        }
    }
    public enum AggrementPeriod{

        YEAR("12개월"),
        YEAR2("24개월"),
        YEAR3("36개월");

        private final String statusMsg;

        AggrementPeriod(String statusMsg){
            this.statusMsg = statusMsg;
        }

        public String getStatusMsg() {
            return statusMsg;
        }
    }
    public enum AggrementType{

        CHOICE("선택약정"),
        PUBNOTI_SUPAMT("공시지원금");

        private final String statusMsg;

        AggrementType(String statusMsg){
            this.statusMsg = statusMsg;
        }

        public String getStatusMsg() {
            return statusMsg;
        }
    }

    public enum Usim{
        EXISTING("기존유심"),
        PRE_PAYMENT("선납"),
        POST_PAYMENT("후납"),
        OTHER_PRE_PAYMENT("타사유심선납"),
        OTHER_POST_PAYMENT("타사유심후납"),
        USIM_PRE_PAYMENT("유심만발송선납"),
        USIM_POST_PAYMENT("유심만발송후납")
        ;

        private final String statusMsg;

        Usim(String statusMsg){
            this.statusMsg = statusMsg;
        }

        public String getStatusMsg() {
            return statusMsg;
        }
    }

    public enum JoinType{
        TRANS_TELECOM("번호이동"),
        NEW_TELECOM("신규가입"),
        EQUALS_TELECOM("기기변경")

        ;

        private final String statusMsg;

        JoinType(String statusMsg){
            this.statusMsg = statusMsg;
        }

        public String getStatusMsg() {
            return statusMsg;
        }
    }

    public enum AdditionCategory{
        INSURACE("보험"),
        ;
        private final String statusMsg;

        AdditionCategory(String statusMsg){
            this.statusMsg = statusMsg;
        }

        public String getStatusMsg() {
            return statusMsg;
        }
    }

    public enum Area{
        SEOUL(11),
        BUSAN(12),
        GYEONGGI_NAM(13),
        GANGWON(14),
        CHONGBUK(15),
        CHONGNAM(16),
        JEONBUK(17),
        JEONNAM(18),
        GYEONGBUK(19),
        GYEONGNAM(20),
        JEJU(21),
        DAEGU(22),
        INCHEON(23),
        GWANGJU(24),
        DAEJEON(25),
        WOOLSAN(26),
        GYEONGGI_BUK(28)
        ;
        private final int statusMsg;

        Area(int statusMsg){
            this.statusMsg = statusMsg;
        }

        public int getStatusMsg() {
            return statusMsg;
        }
    }

    public enum LicenseType{

        REGIST("주민등록증"),
        DRIVER("운전면허증"),
        DISA("장애인등록증"),
        NTNL_MERIT("국가유공자증"),
        FOREIGN("외국인등록증")
        ;
        private final String statusMsg;

        LicenseType(String statusMsg){
            this.statusMsg = statusMsg;
        }

        public String getStatusMsg() {
            return this.statusMsg;
        }
    }
    public enum CustomerType {
        PEROSNAL("개인"),
        MINOR("미성년자"),
        SOLE_TRADER("개인사업자"),
        CORPORATION("법인사업자"),
        FOREIGN("외국인"),
        ;
        private final String statusMsg;

        CustomerType(String statusMsg){
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg(){
            return this.statusMsg;
        }

    }

    public enum PaymentWay {
        A( "계좌이체"),
        C( "카드납부"),
        G( "지로납부"),
        ;
        private final String statusMsg;

        PaymentWay(String statusMsg) {
            this.statusMsg = statusMsg;
        }
        public String getStatusMsg() {
            return this.statusMsg;
        }
    }
}
