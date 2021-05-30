package com.daema.wms.domain.dto.request;

import com.daema.base.dto.SearchParamDto;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnStockRequestDto extends SearchParamDto {
    // 통신사
    private Integer telecom;

    //입고일
    private String inStockRegiDate;

    //이동일
    private String returnStockRegiDate;

    // 공급처
    private Long provId;

    // 이전 보유처
    private Long prevStockId;

    // 현재 보유처
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
    private WmsEnum.InStockStatus returnStockStatus;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 외장상태 = "상" / "중" / "하" / "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    // 원시 바코드 or 정재된 바코드 or 시리얼 넘버  (바코드)
    private String barcode;
}
