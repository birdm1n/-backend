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
public class DeviceCurrentResponseDto {

    //통신사
    private String telecomName;

    private Long dvcId;

    //입고일
    private LocalDateTime inStockRegiDate;

    //출고일
    private LocalDateTime moveRegiDate;

    //입고 경과일
    private Long diffInStockRegiDate;

    public Long getDiffInStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.inStockRegiDate);
    }

    //이동 경과일
    private Long diffMoveRegiDate;

    public Long getDiffMoveRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.moveRegiDate);
    }


    // 이전보유처
    private String prevStockName;

    // 현재보유처
    private String nextStockName;

    //재고구분
    private WmsEnum.StockType stockType;
    private String stockTypeMsg;

    //제조사
    private String makerName;

    //기기명
    private String goodsName;

    //모델명
    private String modelName;

    //용량
    private String capacity;

    //색상
    private String colorName;

    // 기기일련번호(바코드)
    private String rawBarcode;

    //입고단가
    private int inStockAmt;

    //입고상태
    private WmsEnum.InStockStatus inStockStatus;
    private String inStockStatusMsg;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 외장상태 = "상" / "중" / "하" / "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    private String extrrStatusMsg;
    //배송방법
    private WmsEnum.DeliveryType deliveryType;
    private String deliveryTypeMsg;
    //    //배송상태
    private WmsEnum.DeliveryStatus deliveryStatus;
    private String deliveryStatusMsg;

    //구성품누락
    private String productMissYn;

    //누락제품
    private String missProduct;

    //차감비
    private Integer ddctAmt;

    //추가차감금액
    private Integer addDdctAmt;

    //반품비
    private Integer returnStockAmt;

    //출고가차감(Y/N) 체크박스
    private String ddctReleaseAmtYn;


    public String getInStockStatusMsg() {
        return this.inStockStatus != null ? this.inStockStatus.getStatusMsg() : "";
    }

    public String getExtrrStatusMsg() {
        return this.extrrStatus != null ? this.extrrStatus.getStatusMsg() : "";
    }

    public String getStockTypeMsg() {
        return this.stockType != null ? this.stockType.getStatusMsg() : "";
    }

    public String getDeliveryTypeMsg() {
        return this.deliveryType != null ? this.deliveryType.getStatusMsg() : "";
    }

    public String getDeliveryStatusMsg() {
        return this.deliveryStatus != null ? this.deliveryStatus.getStatusMsg() : "";
    }


}
