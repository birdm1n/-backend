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
public class TransResponseDto {

    private Long outStockId;

    // 통신사
    private String telecomName;

    // 공급처
//    private String provName;

    // 상품명_모델명
    private String goodsName;
    private String modelName;

    // 용량
    private String capacity;

    // 색상_기기일련번호(바코드)
    private String colorName;
    private String fullBarcode;

    // 입고단가
    private int inStockAmt;

    // 입고상태 =  1, "정상"/ 2, "개봉"
    private WmsEnum.InStockStatus inStockStatus;
    private String inStockStatusMsg;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 외장상태 = 1, "상" / 2, "중" / 3, "하" / 4, "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    private String extrrStatusMsg;

    // 현재 / 다음 보유처
    private String prevStockName;
    private String nextStockName;

    // 재고구분
    private WmsEnum.StockStatStr statusStr;
    private String statusStrMsg;

    // 고객명
    private String cusName;

    // 고객 전화번호
    private String cusPhone;
    
    // 배송메모
    private String deliveryMemo;

    //제조사
    private String makerName;

    private WmsEnum.MoveStockType moveStockType;
    private String moveStockTypeMsg;
    
    // 이동일
    private LocalDateTime regiDateTime;
    private Long regiUserId;
    private String regiUserName;
    private LocalDateTime updDateTime;
    private Long updUserId;
    private String updUserName;

    // 입고일
    private LocalDateTime inStockRegiDateTime;
    
    // 경과일
    private Long diffInStockRegiDate;

    public Long getDiffInStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.inStockRegiDateTime);
    }

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