package com.daema.wms.domain.dto.response;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStatusListDto {

    private Long dvcStatusId;

    private Long dvcId;

    // 입고상태 =  1, "정상"/ 2, "개봉"
    private WmsEnum.InStockStatus inStockStatus;
    private String inStockStatusMsg;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 구성품 누락여부 = Y / N
    private String productMissYn;

    // 외장상태 = 1, "상" / 2, "중" / 3, "하" / 4, "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    private String extrrStatusMsg;

    // 차감비
    private int ddctAmt;

    // 추가 차감비
    private int addDdctAmt;

    // 출고가 차감 YN
    private String ddctReleaseAmtYn;

    // 누락제품
    private String missProduct;

    public Long getDvcStatusId() {
        return dvcStatusId;
    }

    public void setDvcStatusId(Long dvcStatusId) {
        this.dvcStatusId = dvcStatusId;
    }

    public Long getDvcId() {
        return dvcId;
    }

    public void setDvcId(Long dvcId) {
        this.dvcId = dvcId;
    }

    public WmsEnum.InStockStatus getInStockStatus() {
        return inStockStatus;
    }

    public void setInStockStatus(WmsEnum.InStockStatus inStockStatus) {
        this.inStockStatus = inStockStatus;
    }

    public String getInStockStatusMsg() {
        return this.inStockStatus != null ? this.inStockStatus.getStatusMsg() : "";
    }

    public void setInStockStatusMsg(String inStockStatusMsg) {
        this.inStockStatusMsg = inStockStatusMsg;
    }

    public String getProductFaultyYn() {
        return productFaultyYn;
    }

    public void setProductFaultyYn(String productFaultyYn) {
        this.productFaultyYn = productFaultyYn;
    }

    public String getProductMissYn() {
        return productMissYn;
    }

    public void setProductMissYn(String productMissYn) {
        this.productMissYn = productMissYn;
    }

    public WmsEnum.DeviceExtrrStatus getExtrrStatus() {
        return extrrStatus;
    }

    public void setExtrrStatus(WmsEnum.DeviceExtrrStatus extrrStatus) {
        this.extrrStatus = extrrStatus;
    }

    public String getExtrrStatusMsg() {
        return this.extrrStatus != null ? this.extrrStatus.getStatusMsg() : "";
    }

    public void setExtrrStatusMsg(String extrrStatusMsg) {
        this.extrrStatusMsg = extrrStatusMsg;
    }

    public int getDdctAmt() {
        return ddctAmt;
    }

    public void setDdctAmt(int ddctAmt) {
        this.ddctAmt = ddctAmt;
    }

    public int getAddDdctAmt() {
        return addDdctAmt;
    }

    public void setAddDdctAmt(int addDdctAmt) {
        this.addDdctAmt = addDdctAmt;
    }

    public String getDdctReleaseAmtYn() {
        return ddctReleaseAmtYn;
    }

    public void setDdctReleaseAmtYn(String ddctReleaseAmtYn) {
        this.ddctReleaseAmtYn = ddctReleaseAmtYn;
    }

    public String getMissProduct() {
        return missProduct;
    }

    public void setMissProduct(String missProduct) {
        this.missProduct = missProduct;
    }
}