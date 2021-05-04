package com.daema.wms.repository.custom;


import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.dto.response.InStockWaitGroupDto;
import com.daema.wms.domain.enums.WmsEnum;

import java.util.List;

public interface CustomInStockWaitRepository {

    List<InStockWaitGroupDto> groupInStockWaitList(long storeId, WmsEnum.InStockStatus inStockStatus);

    List<InStockWait> getList(long storeId, WmsEnum.InStockStatus inStockStatus);
}
