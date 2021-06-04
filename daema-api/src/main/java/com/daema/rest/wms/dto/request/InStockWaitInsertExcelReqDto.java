package com.daema.rest.wms.dto.request;

import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStockWaitInsertExcelReqDto {

    @ApiModelProperty(value = "공급처 ID", required = true, example = "0")
    private Long provId;

    @ApiModelProperty(value = "통신사 ID", required = true, example = "0")
    private Long telecom;

    @ApiModelProperty(value = "보유처 ID", required = true, example = "0")
    private Long stockId;

    @ApiModelProperty(value = "입고상태",  required = true)
    private WmsEnum.InStockStatus inStockStatus;

}