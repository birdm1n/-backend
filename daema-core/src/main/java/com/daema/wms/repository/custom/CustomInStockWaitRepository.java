package com.daema.wms.repository.custom;


import com.daema.wms.domain.InStock;
import com.daema.wms.domain.InStockWait;
import com.daema.wms.domain.dto.response.InStockWaitGroupDto;

import java.util.List;

public interface CustomInStockWaitRepository {

    List<InStockWaitGroupDto> groupInStockWaitList(long storeId, InStock.StockStatus inStockStatus);

    List<InStockWait> getList(long storeId, InStock.StockStatus inStockStatus);
}
