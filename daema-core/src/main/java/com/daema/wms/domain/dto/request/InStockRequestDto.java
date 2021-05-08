package com.daema.wms.domain.dto.request;

import com.daema.base.dto.SearchParamDto;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InStockRequestDto extends SearchParamDto {
    // 통신사
    private int telecom;

    //입고일
    private String inStockRegiDate;

    // 공급처
    private Long provId;

    // 보유처
    private Long stockId;
    
    // 재고구분
    private WmsEnum.StockStatStr statusStr;

    //제조사
    private int maker;
    
    //기기명
    private String goodsId;
    
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
    private String fullBarcode;
}
