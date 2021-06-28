package com.daema.core.wms.dto.request;

import com.daema.core.base.dto.SearchParamDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequestDto extends SearchParamDto {

    private Long stockId;
    private Long telecom;
    private Long maker;
    private Long goodsId;
    private String capacity;
    private String colorName;
    private String barcode;

}
