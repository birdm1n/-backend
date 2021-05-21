package com.daema.wms.domain.dto.response;



import com.daema.base.util.CommonUtil;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoveMgmtResponseDto { // 이동현황


    private Long moveStockId;

    // 통신사
    private int telecom;
    private String telecomName;


    // 이전 보유처
    private Long prevStockId;
    private String prevStockName;

    // 현재 보유처
    private Long nextStockId;
    private String nextStockName;

    // 재고구분
    private WmsEnum.MoveStockType moveStockType;
    private String moveStockTypeMsg;

    //제조사
    private int maker;
    private String makerName;

    //기기명(모델명)
    private Long goodsId;
    private String goodsName;
    private String modelName;

    //용량
    private String capacity;

    // 색상_기기일련번호(바코드)
    private long goodsOptionId;
    private String colorName;
    private String commonBarcode;
    private String fullBarcode;

    // 입고단가
    private int inStockAmt;

    // 입고상태 =  1, "정상"/ 2, "개봉"
    private WmsEnum.InStockStatus inStockStatus;
    private String inStockStatusMsg;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;


    // 외장상태 = "상" / "중" / "하" / "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    private String extrrStatusMsg;

    // 구성품 누락여부 = Y / N
    private String productMissYn;


    //배송방법
    private WmsEnum.DeliveryType deliveryType;
    private String deliveryTypeMsg;

    //배송상태
    private WmsEnum.DeliveryStatus deliveryStatus;
    private String deliveryStatusMsg;
    public String getDeliveryTypeMsg() {
        return this.deliveryType != null ? this.deliveryType.getStatusMsg():"";
    }

    public String getDeliveryStatusMsg() {
        return this.deliveryStatus != null ? this.deliveryStatus.getStatusMsg():"";
    }

    ///////
    private LocalDateTime moveRegiDateTime; // 이동일
    private Long diffMoveRegiDateTime;
    private LocalDateTime inStockRegiDateTime; // 입고일
    private Long diffInStockRegiDate;
    private Long regiUserId;
    private String regiUserName;
    private Long updUserId;
    private String updUserName;

    private Long dvcId;

    // 반품비
    private int returnStockAmt;
    // 추가 차감비
    private int addDdctAmt;
    // 출고가 차감 YN
    private String ddctReleaseAmtYn;
    // 누락제품
    private String missProduct;
    //재고구분
    private WmsEnum.StockStatStr statusStr;

    public Long getDiffInStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.inStockRegiDateTime);
    }

    public Long getDiffMoveRegiDateTime() { return CommonUtil.diffDaysLocalDateTime(this.moveRegiDateTime); }

    public String getStatusStrMsg() {
        return this.statusStr != null ? this.statusStr.getStatusMsg() : "";
    }

    public String getMoveStockTypeMsg() {
        return this.moveStockType != null ? this.moveStockType.getStatusMsg() : "";
    }

    public String getInStockStatusMsg() {
        return this.inStockStatus != null ? this.inStockStatus.getStatusMsg() : "";
    }

    public String getExtrrStatusMsg() {
        return this.extrrStatus != null ? this.extrrStatus.getStatusMsg() : "";
    }


}
