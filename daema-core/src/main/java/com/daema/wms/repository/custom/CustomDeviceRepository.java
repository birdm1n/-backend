package com.daema.wms.repository.custom;


import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.dto.request.DeviceCurrentRequestDto;
import com.daema.wms.domain.dto.response.DeviceCurrentResponseDto;
import com.daema.wms.domain.dto.response.DeviceHistoryResponseDto;
import com.daema.wms.domain.dto.response.DeviceListResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomDeviceRepository {
    List<DeviceHistoryResponseDto> getDeviceHistory(Long dvcId, Long storeId);

    Page<DeviceCurrentResponseDto> getDeviceCurrentPage(DeviceCurrentRequestDto deviceCurrentRequestDto);

    long deviceDuplCk(Store store, String barcode, List<Long> goodsOptionId );

    Page<DeviceListResponseDto> getDeviceWithBarcode(String barcode, Store store);
}
