package com.daema.core.wms.dto.response;

import com.daema.core.base.util.CommonUtil;
import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveStockResponseDto {

    private Long moveStockId;

    private Long dvcId;

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
    private String rawBarcode;

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

    // 배송타입
    private WmsEnum.DeliveryType deliveryType;
    // 고객명
    private String cusName;

    // 고객 전화번호
    private String cusPhone;
    private String cusPhone1;
    private String cusPhone2;
    private String cusPhone3;

    private String zipCode;
    private String addr1;
    private String addr2;

    
    // 배송메모
    private String deliveryMemo;

    //제조사
    private String makerName;

    private WmsEnum.MoveStockType moveStockType;
    private String moveStockTypeMsg;
    
    private Long regiUserId;
    private String regiUserName;
    private LocalDateTime updDateTime;
    private Long updUserId;
    private String updUserName;
    // 추가 - 판매이동 수정 불가처리 위해 (판매이동 상태만 수정)
    private String updateYn;
    // 추가 - 개통처리 기능추가
    // 개통 가능 여부
    private String openingYn;

    // 개통 flag 텍스트
    private String openingText;

    // 개통 일자
    private LocalDate openingDate;

    // 입고일
    private LocalDateTime inStockRegiDateTime;
    // 입고 경과일
    private Long diffInStockRegiDate;
    public Long getDiffInStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.inStockRegiDateTime);
    }
    // 출고일
    private LocalDateTime regiDateTime;
    // 출고경과일
    private LocalDateTime diffRegiDate;
    public Long getDiffRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.regiDateTime);
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