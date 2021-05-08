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


}
