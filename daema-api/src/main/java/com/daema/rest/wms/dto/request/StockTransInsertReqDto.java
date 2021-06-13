package com.daema.rest.wms.dto.request;

import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransInsertReqDto {
    @ApiModelProperty(value = "배송타입",  required = true)
    private WmsEnum.DeliveryType deliveryType;

    @ApiModelProperty(value = "이관처 ID", example = "0", required = true)
    private Long transStoreId;

    @ApiModelProperty(value = "원시 바코드 or 정재된 바코드 or 시리얼 넘버", required = false)
    private String barcode;

    @ApiModelProperty(value = "기기 ID", required = true)
    private String selDvcId;

    @ApiModelProperty(value = "택배사", example = "0",required = true)
    private Long courier;

    @ApiModelProperty(value = "송장번호", required = true)
    private String invoiceNo;

    @ApiModelProperty(value = "메모입력")
    private String deliveryMemo;
}
