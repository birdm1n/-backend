package com.daema.core.wms.repository.custom;


import com.daema.core.wms.domain.InStockWait;
import com.daema.core.wms.dto.response.InStockWaitGroupDto;
import com.daema.core.wms.domain.enums.WmsEnum;

import java.util.List;

public interface CustomInStockWaitRepository {

    List<InStockWaitGroupDto> groupInStockWaitList(long memberId, long storeId, WmsEnum.InStockStatus inStockStatus);

    List<InStockWait> getList(long memberId, long storeId, WmsEnum.InStockStatus inStockStatus);

    long inStockWaitDuplCk(long storeId, String barcode, Long goodsId);
}
