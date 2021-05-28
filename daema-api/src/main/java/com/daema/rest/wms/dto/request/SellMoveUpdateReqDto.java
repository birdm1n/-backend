package com.daema.rest.wms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellMoveUpdateReqDto {
    @ApiModelProperty(value = "재고이동 ID", required = true, example = "0")
    private Long moveStockId;

    @ApiModelProperty(value = "고객명",  required = true)
    private String cusName;

    @ApiModelProperty(value = "고객 전화번호",  required = true)
    private String cusPhone;

    @ApiModelProperty(value = "고객 전화번호1",  required = true)
    private String cusPhone1;

    @ApiModelProperty(value = "고객 전화번호2",  required = true)
    private String cusPhone2;

    @ApiModelProperty(value = "고객 전화번호3",  required = true)
    private String cusPhone3;

    @ApiModelProperty(value = "우편번호",  required = true)
    private String zipCode;

    @ApiModelProperty(value = "주소1",  required = true)
    private String addr1;

    @ApiModelProperty(value = "주소2",  required = true)
    private String addr2;

    @ApiModelProperty(value = "메모입력")
    private String deliveryMemo;
}
