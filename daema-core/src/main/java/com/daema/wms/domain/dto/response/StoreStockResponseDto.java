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
public class StoreStockResponseDto {

    private Long inStockId;

    private Long returnStockId;

    private Long storeStockId;

    private WmsEnum.StockType stockType;

    private Long dvcId;

    // 입고메모
    private String inStockMemo;

    // 이전 보유처
    private Long prevStockId;
    private String prevStockName;

    // 현재 보유처
    private Long nextStockId;
    private String nextStockName;

    /**
     Desc : 기기별 입력정보 및 모델별 입력정보
     */
    // 통신사
    private int telecom;
    private String telecomName;

    // 공급처
    private Long provId;

    // 보유처
    private Long stockId;
    private String stockName;

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

    // 반품메모
    private String returnStockMemo;

    private LocalDateTime regiDateTime;
    private Long regiUserId;
    private String regiUserName;
    private LocalDateTime updDateTime;
    private Long updUserId;
    private String updUserName;
    private LocalDateTime inStockRegiDateTime;

    private Long diffInStockRegiDate;
    private Long diffStockCheckDateTime1;
    private Long diffStockCheckDateTime2;

    private LocalDateTime stockCheckDateTime1;
    private LocalDateTime stockCheckDateTime2;

    private DeviceStatusListDto deviceStatusListDto;

    public Long getReturnStockId() {
        return returnStockId;
    }

    public void setReturnStockId(Long returnStockId) {
        this.returnStockId = returnStockId;
    }

    public String getReturnStockMemo() {
        return returnStockMemo;
    }

    public void setReturnStockMemo(String returnStockMemo) {
        this.returnStockMemo = returnStockMemo;
    }

    public Long getInStockId() {
        return inStockId;
    }

    public void setInStockId(Long inStockId) {
        this.inStockId = inStockId;
    }

    public Long getStoreStockId() {
        return storeStockId;
    }

    public void setStoreStockId(Long storeStockId) {
        this.storeStockId = storeStockId;
    }

    public WmsEnum.StockType getStockType() {
        return stockType;
    }

    public void setStockType(WmsEnum.StockType stockType) {
        this.stockType = stockType;
    }

    public Long getDvcId() {
        return dvcId;
    }

    public void setDvcId(Long dvcId) {
        this.dvcId = dvcId;
    }

    public String getInStockMemo() {
        return inStockMemo;
    }

    public void setInStockMemo(String inStockMemo) {
        this.inStockMemo = inStockMemo;
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

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
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

    public LocalDateTime getUpdDateTime() {
        return updDateTime;
    }

    public void setUpdDateTime(LocalDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }

    public Long getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(Long updUserId) {
        this.updUserId = updUserId;
    }

    public String getUpdUserName() {
        return updUserName;
    }

    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    public LocalDateTime getInStockRegiDateTime() {
        return inStockRegiDateTime;
    }

    public void setInStockRegiDateTime(LocalDateTime inStockRegiDateTime) {
        this.inStockRegiDateTime = inStockRegiDateTime;
    }

    public Long getDiffInStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.inStockRegiDateTime);
    }

    public void setDiffInStockRegiDate(Long diffInStockRegiDate) {
        this.diffInStockRegiDate = diffInStockRegiDate;
    }

    public Long getDiffStockCheckDateTime1() {
        return CommonUtil.diffDaysLocalDateTime(this.stockCheckDateTime1);
    }

    public void setDiffStockCheckDateTime1(Long diffStockCheckDateTime1) {
        this.diffStockCheckDateTime1 = diffStockCheckDateTime1;
    }

    public Long getDiffStockCheckDateTime2() {
        return CommonUtil.diffDaysLocalDateTime(this.stockCheckDateTime2);
    }

    public void setDiffStockCheckDateTime2(Long diffStockCheckDateTime2) {
        this.diffStockCheckDateTime2 = diffStockCheckDateTime2;
    }

    public LocalDateTime getStockCheckDateTime1() {
        return stockCheckDateTime1;
    }

    public void setStockCheckDateTime1(LocalDateTime stockCheckDateTime1) {
        this.stockCheckDateTime1 = stockCheckDateTime1;
    }

    public LocalDateTime getStockCheckDateTime2() {
        return stockCheckDateTime2;
    }

    public void setStockCheckDateTime2(LocalDateTime stockCheckDateTime2) {
        this.stockCheckDateTime2 = stockCheckDateTime2;
    }

    public DeviceStatusListDto getDeviceStatusListDto() {
        return deviceStatusListDto;
    }

    public void setDeviceStatusListDto(DeviceStatusListDto deviceStatusListDto) {
        this.deviceStatusListDto = deviceStatusListDto;
    }
}