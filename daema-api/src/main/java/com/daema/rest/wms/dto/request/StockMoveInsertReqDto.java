package com.daema.rest.wms.dto.request;

import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMoveInsertReqDto {
    @ApiModelProperty(value = "배송타입",  required = true)
    private WmsEnum.DeliveryType deliveryType;

    @ApiModelProperty(value = "보유처 ID", example = "0",required = true)
    private Long prevStockId;

    @ApiModelProperty(value = "이동처 ID", required = true)
    private Long nextStockId;

    @ApiModelProperty(value = "메모입력")
    private String deliveryMemo;
}
