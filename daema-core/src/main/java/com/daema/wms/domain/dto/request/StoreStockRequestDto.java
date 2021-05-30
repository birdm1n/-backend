package com.daema.wms.domain.dto.request;

import com.daema.base.dto.SearchParamDto;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreStockRequestDto extends SearchParamDto {
    // 통신사
    private Integer telecom;

    //입고일
    private String inStockRegiDate;

    //이동일
    private String moveStockRegiDate;

    //재고확인일
    private String storeStockCheckDate;

    // 현재 보유처
    private Long prevStockId;

    // 다음 보유처
    private Long nextStockId;

    // 재고구분
    private WmsEnum.StockStatStr statusStr;

    //제조사
    private Integer maker;

    //기기명
    private Long goodsId;

    //용량
    private String capacity;

    //색상
    private String colorName;

    //입고상태
    private WmsEnum.InStockStatus inStockStatus;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 외장상태 = "상" / "중" / "하" / "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    // 기기일련번호(바코드)
    private String barcode;

    // 배송상태
    private WmsEnum.DeliveryStatus deliveryStatus;

    // 판정상태
    private WmsEnum.JudgementStatus judgeStatus;

    // 판정메모
    private String judgeMemo;

    // 공급처
    private Long provId;

    // 기기ID
    private Long dvcId;

}
