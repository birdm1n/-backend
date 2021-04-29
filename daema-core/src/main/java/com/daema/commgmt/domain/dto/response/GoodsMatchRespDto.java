package com.daema.commgmt.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsMatchRespDto {

    // 공통
    private int maker;
    private String makerName;
    private int telecom;
    private String telecomName;
    private long goodsId;
    private String goodsName;
    private String modelName;
    private String capacity;
    private long goodsOptionId;
    private String colorName;
    private String commonBarcode;
}
