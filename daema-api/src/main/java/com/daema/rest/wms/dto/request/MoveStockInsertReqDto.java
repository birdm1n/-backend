package com.daema.rest.wms.dto.request;

import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MoveStockInsertReqDto {

    @ApiModelProperty(value = "배송정보",  required = true)
    private WmsEnum.MoveStockType moveStockType;

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

    @ApiModelProperty(value = "외장상태",  required = true)
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @ApiModelProperty(value = "제품상태")
    private String productFaultyYn ;

    @ApiModelProperty(value = "차감비")
    private int ddctAmt;

    @ApiModelProperty(value = "추가 차감비")
    private int addDdctAmt;

    @ApiModelProperty(value = "출고가 차감 여부")
    private String ddctReleaseAmtYn = "N";

    @ApiModelProperty(value = "구성품 누락여부")
    private String productMissYn = "N";

    @ApiModelProperty(value = "누락제품")
    private String missProduct;

    @ApiModelProperty(value = "입고메모")
    private String inStockMemo;

    // 바코드 정보가 없을 경우
    @ApiModelProperty(value = "상품 ID")
    private Long goodsId;
    
    @ApiModelProperty(value = "용량")
    private String capacity;

    @ApiModelProperty(value = "색상")
    private String colorName;

    //todo setter 추가
}