package com.daema.rest.wms.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMatchResponseDto {

    private Integer maker;
    private String makerName;
    private Integer telecom;
    private String telecomName;
    private Long goodsId;
    private String goodsName;
    private String modelName;
    private String capacity;
    private Long goodsOptionId;
    private String colorName;
    private Long stockId;
    private String stockName;
    private Long storeId;
    private String storeName;
    private Long provId;
    private String provName;


}
