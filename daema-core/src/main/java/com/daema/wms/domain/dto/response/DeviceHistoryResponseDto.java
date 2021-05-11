package com.daema.wms.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long diffStockRegiDate;









}