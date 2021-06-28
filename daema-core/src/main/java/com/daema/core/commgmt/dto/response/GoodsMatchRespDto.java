package com.daema.core.commgmt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsMatchRespDto {

    private Long maker;
    private String makerName;
    private Long telecom;
    private String telecomName;
    private long goodsId;
    private String goodsName;
    private String modelName;
    private String capacity;
    private long goodsOptionId;
    private String colorName;
    private String commonBarcode;
}
