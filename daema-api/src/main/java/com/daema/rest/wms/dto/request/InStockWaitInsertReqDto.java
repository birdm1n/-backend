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

    

    public void setProvId(Long provId) {
        this.provId = provId;
    }

    public void setTelecom(int telecom) {
        this.telecom = telecom;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public void setFullBarcode(String fullBarcode) {
        this.fullBarcode = fullBarcode;
    }

    public void setBarcodeType(WmsEnum.BarcodeType barcodeType) {
        this.barcodeType = barcodeType;
    }

    public void setInStockStatus(WmsEnum.InStockStatus inStockStatus) {
        this.inStockStatus = inStockStatus;
    }

    public void setExtrrStatus(WmsEnum.DeviceExtrrStatus extrrStatus) {
        this.extrrStatus = extrrStatus;
    }

    public void setProductFaultyYn(String productFaultyYn) {
        if(StringUtils.isEmpty(productFaultyYn)){
            this.productFaultyYn = "N";
        }else {
            this.productFaultyYn = productFaultyYn;
        }

    }

    public void setDdctAmt(int ddctAmt) {
        this.ddctAmt = ddctAmt;
    }

    public void setAddDdctAmt(int addDdctAmt) {
        this.addDdctAmt = addDdctAmt;
    }

    public void setDdctReleaseAmtYn(String ddctReleaseAmtYn) {
        if(StringUtils.isEmpty(ddctReleaseAmtYn)){
            this.ddctReleaseAmtYn = "N";
        }else{
            this.ddctReleaseAmtYn = ddctReleaseAmtYn;
        }
    }

    public void setProductMissYn(String productMissYn) {
        if(StringUtils.isEmpty(productMissYn)){
            this.productMissYn = "N";
        }else{
            this.productMissYn = productMissYn;
        }

    }

    public void setMissProduct(String missProduct) {
        this.missProduct = missProduct;
    }

    public void setInStockMemo(String inStockMemo) {
        this.inStockMemo = inStockMemo;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}