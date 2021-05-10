package com.daema.rest.wms.dto.request;

import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStockWaitInsertReqDto {

    @ApiModelProperty(value = "공급처 ID",  required = true)
    private Long provId;

    @ApiModelProperty(value = "통신사 ID",  required = true)
    private int telecom;

    @ApiModelProperty(value = "보유처 ID",  required = true)
    private Long stockId;

    @ApiModelProperty(value = "기기일련번호(바코드)",  required = true)
    private String fullBarcode;

    @ApiModelProperty(value = "바코드입력 구분",  required = true)
    private WmsEnum.BarcodeType barcodeType;

    @ApiModelProperty(value = "입고상태",  required = true)
    private WmsEnum.InStockStatus inStockStatus;

    @ApiModelProperty(value = "제품상태")
    private String productFaultyYn;

    @ApiModelProperty(value = "외장상태",  required = true)
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @ApiModelProperty(value = "차감비")
    private int ddctAmt;

    @ApiModelProperty(value = "추가 차감비")
    private int addDdctAmt;

    @ApiModelProperty(value = "출고가 차감 여부")
    private String outStockAmtYn;

    @ApiModelProperty(value = "구성품 누락여부")
    private String productMissYn;
    
    @ApiModelProperty(value = "누락제품")
    private String missProduct;

    @ApiModelProperty(value = "입고메모")
    private String inStockMemo;

}