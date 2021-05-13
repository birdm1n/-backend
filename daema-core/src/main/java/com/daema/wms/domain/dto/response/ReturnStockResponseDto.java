package com.daema.wms.domain.dto.response;

import com.daema.base.util.CommonUtil;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnStockResponseDto {

    private Long returnStockId;

    private Long dvcId;

    // 차감비
    private int ddctAmt;

    // 추가 차감비
    private int addDdctAmt;

    // 출고가 차감 YN
    private String ddctReleaseAmtYn;

    // 누락제품
    private String missProduct;

    // 입고메모
    private String returnStockMemo;

    // 소유권을 가지는 Store = 관리점
//    private Long ownStoreId;
//
//    // 보유처 StoreId
//    private Long holdStoreId;

    /**
     Desc : 기기별 입력정보 및 모델별 입력정보
     */
    // 통신사
    private int telecom;
    private String telecomName;

    // 공급처
    private Long provId;

    // 이전 보유처
    private Long prevStockId;
    private String prevStockName;

    // 현재 보유처
    private Long nextStockId;
    private String nextStockName;

    // 재고구분
    private String statusStr;

    //제조사
    private int maker;
    private String makerName;


    // 상품명_모델명
    private long goodsId;
    private String goodsName;
    private String modelName;

    // 용량
    private String capacity;

    // 색상_기기일련번호(바코드)
    private long goodsOptionId;
    private String colorName;
    private String commonBarcode;
    private String fullBarcode;

    // 입고단가
    private int inStockAmt;

    // 반품비
    private int returnStockAmt;

    // 입고상태 =  1, "정상"/ 2, "개봉"
    private WmsEnum.InStockStatus returnStockStatus;
    private String returnStockStatusMsg;

    // 제품상태 =  N, "-" / Y, "불량"
    private String productFaultyYn;

    // 구성품 누락여부 = Y / N
    private String productMissYn;

    // 외장상태 = 1, "상" / 2, "중" / 3, "하" / 4, "파손"
    private WmsEnum.DeviceExtrrStatus extrrStatus;
    private String extrrStatusMsg;

    private LocalDateTime regiDateTime;
    private Long regiUserId;
    private String regiUserName;
    private LocalDateTime inStockRegiDateTime;

    private Long diffReturnStockRegiDate;
    private Long diffInStockRegiDate;


    public Long getReturnStockId() {
        return returnStockId;
    }

    public void setReturnStockId(Long returnStockId) {
        this.returnStockId = returnStockId;
    }

    public Long getDvcId() {
        return dvcId;
    }

    public void setDvcId(Long dvcId) {
        this.dvcId = dvcId;
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

    public String getReturnStockMemo() {
        return returnStockMemo;
    }

    public void setReturnStockMemo(String returnStockMemo) {
        this.returnStockMemo = returnStockMemo;
    }

    public int getTelecom() {
        return telecom;
    }

    public void setTelecom(int telecom) {
        this.telecom = telecom;
    }

    public String getTelecomName() {
        return telecomName;
    }

    public void setTelecomName(String telecomName) {
        this.telecomName = telecomName;
    }

    public Long getProvId() {
        return provId;
    }

    public void setProvId(Long provId) {
        this.provId = provId;
    }

    public Long getPrevStockId() {
        return prevStockId;
    }

    public void setPrevStockId(Long prevStockId) {
        this.prevStockId = prevStockId;
    }

    public String getPrevStockName() {
        return prevStockName;
    }

    public void setPrevStockName(String prevStockName) {
        this.prevStockName = prevStockName;
    }

    public Long getNextStockId() {
        return nextStockId;
    }

    public void setNextStockId(Long nextStockId) {
        this.nextStockId = nextStockId;
    }

    public String getNextStockName() {
        return nextStockName;
    }

    public void setNextStockName(String nextStockName) {
        this.nextStockName = nextStockName;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getMaker() {
        return maker;
    }

    public void setMaker(int maker) {
        this.maker = maker;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public long getGoodsOptionId() {
        return goodsOptionId;
    }

    public void setGoodsOptionId(long goodsOptionId) {
        this.goodsOptionId = goodsOptionId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getCommonBarcode() {
        return commonBarcode;
    }

    public void setCommonBarcode(String commonBarcode) {
        this.commonBarcode = commonBarcode;
    }

    public String getFullBarcode() {
        return fullBarcode;
    }

    public void setFullBarcode(String fullBarcode) {
        this.fullBarcode = fullBarcode;
    }

    public int getInStockAmt() {
        return inStockAmt;
    }

    public void setInStockAmt(int inStockAmt) {
        this.inStockAmt = inStockAmt;
    }

    public int getReturnStockAmt() {
        return returnStockAmt;
    }

    public void setReturnStockAmt(int returnStockAmt) {
        this.returnStockAmt = returnStockAmt;
    }

    public WmsEnum.InStockStatus getReturnStockStatus() {
        return returnStockStatus;
    }

    public void setReturnStockStatus(WmsEnum.InStockStatus returnStockStatus) {
        this.returnStockStatus = returnStockStatus;
    }

    public String getReturnStockStatusMsg() {
        return this.returnStockStatus != null ? this.returnStockStatus.getStatusMsg() : "";
    }

    public void setReturnStockStatusMsg(String returnStockStatusMsg) {
        this.returnStockStatusMsg = returnStockStatusMsg;
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

    public LocalDateTime getRegiDateTime() {
        return regiDateTime;
    }

    public void setRegiDateTime(LocalDateTime regiDateTime) {
        this.regiDateTime = regiDateTime;
    }

    public Long getRegiUserId() {
        return regiUserId;
    }

    public void setRegiUserId(Long regiUserId) {
        this.regiUserId = regiUserId;
    }

    public String getRegiUserName() {
        return regiUserName;
    }

    public void setRegiUserName(String regiUserName) {
        this.regiUserName = regiUserName;
    }

    public LocalDateTime getInStockRegiDateTime() {
        return inStockRegiDateTime;
    }

    public void setInStockRegiDateTime(LocalDateTime inStockRegiDateTime) {
        this.inStockRegiDateTime = inStockRegiDateTime;
    }

    public Long getDiffReturnStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.regiDateTime);
    }

    public void setDiffReturnStockRegiDate(Long diffReturnStockRegiDate) {
        this.diffReturnStockRegiDate = diffReturnStockRegiDate;
    }

    public Long getDiffInStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.inStockRegiDateTime);
    }

    public void setDiffInStockRegiDate(Long diffInStockRegiDate) {
        this.diffInStockRegiDate = diffInStockRegiDate;
    }
}