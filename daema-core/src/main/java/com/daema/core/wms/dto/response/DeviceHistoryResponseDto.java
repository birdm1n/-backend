package com.daema.core.wms.dto.response;

import com.daema.core.base.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceHistoryResponseDto {

    private String stockTypeMsg;
    private LocalDateTime regiDateTime;
    private String regiUserName;
    private String storeName;
    private String memo;
    private Long diffStockRegiDate;

    public Long getDiffStockRegiDate() {
        return CommonUtil.diffDaysLocalDateTime(this.regiDateTime);
    }

    public String getMemo() {
        return StringUtils.hasText(memo) ? memo : "";
    }
}