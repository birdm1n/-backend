package com.daema.core.wms.dto.request;

import com.daema.core.base.dto.SearchParamDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceCurrentRequestDto extends SearchParamDto {

    //입고일
    private String inStockRegiDate;

    //이동일
    private String moveRegiDate;

    // 공급처
    private Long provId;

    // 보유처
    private Long nextStockId;

    // 재고구분
    private WmsEnum.StockType stockType;

    //통신사
    private Long telecom;

    //기기명
    private Long goodsId;

    //모델명
    private String modelName;


    //용량
    private String capacity;

    //색상
    private String colorName;

    // 기기일련번호(바코드)
    private String barcode;

    //제조사
    private Long maker;

    //입고상태
    private WmsEnum.InStockStatus inStockStatus;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 외장상태 = "상" / "중" / "하" / "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    private WmsEnum.DeliveryType deliveryType;

    private WmsEnum.DeliveryStatus deliveryStatus;


    //기기이력 상세조회
    //DeviceStatus
    private String productMissYn = "N";

    private String missProduct = null;

    private Integer ddctAmt;

    private Integer addDdctAmt;

    //ReturnStock
    private Integer returnStockAmt;

    private String ddctReleaseAmtYn;


}
