package com.daema.core.scm.dto;


import com.daema.core.base.enums.TypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDeliveryDto {

    @ApiModelProperty(value = "신청서 아이디", example = "0")
    private Long applId;

    @ApiModelProperty(value = "택배사 코드 아이디", example = "0")
    private Long courierCodeId;

    @ApiModelProperty(value = "배송 주소")
    private String deliveryAddr;

    @ApiModelProperty(value = "배송 주소 상세")
    private String deliveryAddrDetail;

    @ApiModelProperty(value = "배송 우편 코드")
    private String deliveryZipCode;

    @ApiModelProperty(value = "배송 타입")
    private TypeEnum.DeliveryType deliveryType;

    @ApiModelProperty(value = "송장 번호")
    private String invoiceNum;
}
