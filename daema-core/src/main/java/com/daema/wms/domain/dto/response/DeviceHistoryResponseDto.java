package com.daema.wms.domain.dto.response;

import com.daema.base.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceHistoryResponseDto {

    private String stockTypeMsg;
    private LocalDateTime regiDateTime;
    private String regiUserName;
    private String storeName;
    private Long diffStockRegiDate;

    public String getStockTypeMsg() {
        return stockTypeMsg;
    }

    public void setStockTypeMsg(String stockTypeMsg) {
        this.stockTypeMsg = stockTypeMsg;
    }

    public LocalDateTime getRegiDateTime() {
        return regiDateTime;
    }

    public void setRegiDateTime(LocalDateTime regiDateTime) {
        this.regiDateTime = regiDateTime;
    }

    public String getRegiUserName() {
        return regiUserName;
    }

    public void setRegiUserName(String regiUserName) {
        this.regiUserName = regiUserName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getDiffStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.regiDateTime);
    }

    public void setDiffStockRegiDate(Long diffStockRegiDate) {
        this.diffStockRegiDate = diffStockRegiDate;
    }
}