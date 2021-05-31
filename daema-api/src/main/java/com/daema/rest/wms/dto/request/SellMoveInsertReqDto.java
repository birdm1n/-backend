package com.daema.rest.wms.dto.request;

import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellMoveInsertReqDto {
    @ApiModelProperty(value = "배송타입",  required = true)
    private WmsEnum.DeliveryType deliveryType;

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

    @ApiModelProperty(value = "원시 바코드 or 정재된 바코드 or 시리얼 넘버", required = true)
    private String barcode;

    @ApiModelProperty(value = "유심일련번호",  required = true)
    private String usimFullBarcode;

    @ApiModelProperty(value = "우편번호",  required = true)
    private String zipCode;

    @ApiModelProperty(value = "주소1",  required = true)
    private String addr1;

    @ApiModelProperty(value = "주소2",  required = true)
    private String addr2;

    @ApiModelProperty(value = "택배사", example = "0",required = true)
    private Integer courier;

    @ApiModelProperty(value = "송장번호", required = true)
    private String invoiceNo;

    @ApiModelProperty(value = "메모입력")
    private String deliveryMemo;
}
