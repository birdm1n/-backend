package com.daema.wms.repository.custom;


import com.daema.wms.domain.dto.response.DeviceHistoryResponseDto;

import java.util.List;

public interface CustomDeviceRepository {
    List<DeviceHistoryResponseDto> getDeviceHistory(Long dvcId, Long storeId);
}
