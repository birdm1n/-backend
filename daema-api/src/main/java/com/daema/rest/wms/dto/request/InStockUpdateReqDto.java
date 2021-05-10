package com.daema.rest.wms.dto.request;

import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InStockUpdateReqDto {
    // inStock 테이블
    @ApiModelProperty(value = "입고 ID",  required = true)
    private Long inStockId;

    @ApiModelProperty(value = "입고상태")
    private WmsEnum.InStockStatus inStockStatus;

    @ApiModelProperty(value = "입고단가")
    private int inStockAmt;

    @ApiModelProperty(value = "입고메모")
    private String inStockMemo;

    // deviceStatus 테이블
    @ApiModelProperty(value = "외장상태")
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @ApiModelProperty(value = "제품상태")
    private String productFaultyYn;

    @ApiModelProperty(value = "차감비")
    private int ddctAmt;

    @ApiModelProperty(value = "추가 차감비")
    private int addDdctAmt;

    @ApiModelProperty(value = "구성품 누락여부")
    private String productMissYn;

    @ApiModelProperty(value = "누락제품")
    private String missProduct;




}