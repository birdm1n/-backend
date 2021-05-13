package com.daema.wms.repository.custom;


import com.daema.wms.domain.dto.response.DeviceStatusListDto;

import java.util.List;

public interface CustomDeviceStatusRepository {
    List<DeviceStatusListDto> getLastDeviceStatusInfo(List<Long> dvcIds);
}
