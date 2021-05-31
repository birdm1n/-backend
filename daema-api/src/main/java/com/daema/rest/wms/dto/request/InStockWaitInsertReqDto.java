package com.daema.rest.wms.dto.request;

import com.daema.wms.domain.enums.WmsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InStockWaitInsertReqDto {

    @NotNull(message = "공급처를 입력해주세요")
    @ApiModelProperty(value = "공급처 ID", example = "0",required = true)
    private Long provId;

    @NotNull(message = "통신사를 입력해주세요")
    @ApiModelProperty(value = "통신사 ID", example = "0", required = true)
    private int telecom;

    @NotNull(message = "보유처를 입력해주세요")
    @ApiModelProperty(value = "보유처 ID", example = "0", required = true)
    private Long stockId;

    @ApiModelProperty(value = "원시 바코드 or 정재된 바코드 or 시리얼 넘버", required = true)
    private String barcode;

    @ApiModelProperty(value = "바코드입력 구분", required = true)
    private WmsEnum.BarcodeType barcodeType;

    @ApiModelProperty(value = "입고상태", required = true)
    private WmsEnum.InStockStatus inStockStatus;

    @ApiModelProperty(value = "외장상태", required = true)
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @ApiModelProperty(value = "제품상태")
    private String productFaultyYn = "N";

    @ApiModelProperty(value = "차감비", example = "0")
    private int ddctAmt;

    @ApiModelProperty(value = "추가 차감비", example = "0")
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
    @ApiModelProperty(value = "상품 ID", example = "0")
    private Long goodsId;

    @ApiModelProperty(value = "용량")
    private String capacity;

    @ApiModelProperty(value = "색상")
    private String colorName;

    /*     바코드 가공용     */
    @ApiModelProperty(value = "원시 바코드", example = "")
    private String rawBarcode;
    @ApiModelProperty(value = "원시, 가공 바코드", example = "")
    private String fullBarcode;
    @ApiModelProperty(value = "시리얼_ex : 뒷 7자리", example = "")
    private String serialNo;

    public void setProductFaultyYn(String productFaultyYn) {
        if (StringUtils.isEmpty(productFaultyYn)) {
            this.productFaultyYn = "N";
        } else {
            this.productFaultyYn = productFaultyYn;
        }

    }

    public void setDdctReleaseAmtYn(String ddctReleaseAmtYn) {
        if (StringUtils.isEmpty(ddctReleaseAmtYn)) {
            this.ddctReleaseAmtYn = "N";
        } else {
            this.ddctReleaseAmtYn = ddctReleaseAmtYn;
        }
    }

    public void setProductMissYn(String productMissYn) {
        if (StringUtils.isEmpty(productMissYn)) {
            this.productMissYn = "N";
        } else {
            this.productMissYn = productMissYn;
        }
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
        this.rawBarcode = barcode;
    }
}