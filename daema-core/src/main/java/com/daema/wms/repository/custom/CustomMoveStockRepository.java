package com.daema.wms.repository.custom;


import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.dto.response.SelectStockDto;
import com.daema.wms.domain.enums.WmsEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomMoveStockRepository {
    Page<MoveStockResponseDto> getMoveTypeList(WmsEnum.MovePathType movePathType);

    Page<MoveStockResponseDto> getTransTypeList(WmsEnum.MovePathType movePathType);

    List<Store> getTransStoreList(long storeId);
}
