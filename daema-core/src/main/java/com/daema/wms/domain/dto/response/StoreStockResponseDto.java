package com.daema.wms.domain.dto.response;

import com.daema.base.util.CommonUtil;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreStockResponseDto {

    private Long inStockId;

    private Long returnStockId;

    private Long storeStockId;

    private WmsEnum.StockType stockType;

    private Long dvcId;

    // 입고메모
    private String inStockMemo;

    // 이전 보유처
    private Long prevStockId;
    private String prevStockName;

    // 현재 보유처
    private Long nextStockId;
    private String nextStockName;

    /**
     Desc : 기기별 입력정보 및 모델별 입력정보
     */
    // 통신사
    private int telecom;
    private String telecomName;

    // 공급처
    private Long provId;
    private String provName;

    // 보유처
    private Long stockId;
    private String stockName;

    // 재고구분
    private String statusStr;

    //제조사
    private int maker;
    private String makerName;


    // 상품명_모델명
    private long goodsId;
    private String goodsName;
    private String modelName;

    // 용량
    private String capacity;

    // 색상_기기일련번호(바코드)
    private long goodsOptionId;
    private String colorName;
    private String commonBarcode;
    private String fullBarcode;

    // 입고단가
    private int inStockAmt;

    // 반품비
    private int returnStockAmt;

    // 반품메모
    private String returnStockMemo;

    //배송정보
    private WmsEnum.DeliveryType deliveryType;
    private String deliveryTypeMsg;

    //택배사 codeSeq
    private Integer courier;

    //송장번호
    private String invoiceNo;

    private String deliveryMemo;

    private WmsEnum.DeliveryStatus deliveryStatus;
    private String deliveryStatusMsg;

    //판정상태
    private WmsEnum.JudgementStatus judgeStatus;
    private String judgeStatusMsg;
    private String judgeMemo;

    private LocalDateTime regiDateTime;
    private Long regiUserId;
    private String regiUserName;
    private LocalDateTime updDateTime;
    private Long updUserId;
    private String updUserName;
    private LocalDateTime inStockRegiDateTime;

    private Long diffInStockRegiDate;
    private Long diffStockCheckDateTime1;
    private Long diffStockCheckDateTime2;
    private Long diffMoveDateTime;

    private LocalDateTime stockCheckDateTime1;
    private LocalDateTime stockCheckDateTime2;
    private LocalDateTime moveDateTime;

    private DeviceStatusListDto deviceStatusListDto;

    public Long getDiffInStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.inStockRegiDateTime);
    }

    public Long getDiffStockCheckDateTime1() {
        return CommonUtil.diffDaysLocalDateTime(this.stockCheckDateTime1);
    }

    public Long getDiffStockCheckDateTime2() {
        return CommonUtil.diffDaysLocalDateTime(this.stockCheckDateTime2);
    }

    public Long getDiffMoveDateTime() {
        return CommonUtil.diffDaysLocalDateTime(this.moveDateTime);
    }

    public WmsEnum.JudgementStatus getJudgeStatus() {

        WmsEnum.JudgementStatus tmpJudgeStatus = WmsEnum.JudgementStatus.NONE;

        if(judgeStatus != null){
            tmpJudgeStatus = judgeStatus;
        }
        return tmpJudgeStatus;
    }

    public String getDeliveryTypeMsg() {
        return this.deliveryType != null ? this.deliveryType.getStatusMsg() : "";
    }

    public String getDeliveryStatusMsg() {
        return this.deliveryStatus != null ? this.deliveryStatus.getStatusMsg() : "";
    }

    public String getJudgeStatusMsg() {
        return this.judgeStatus != null ? this.judgeStatus.getStatusMsg() : WmsEnum.JudgementStatus.NONE.getStatusMsg();
    }
}





















