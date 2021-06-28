package com.daema.core.wms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceListResponseDto {

    private Long dvcId;

    private String telecomName;
    private String makerName;
    private String networkName;

    private String goodsName;
    private String modelName;

    private String colorName;
    private String capacity;

    private String rawBarcode;
    private String unLockYn;
}