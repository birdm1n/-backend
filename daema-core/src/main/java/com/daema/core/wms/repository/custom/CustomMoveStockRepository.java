package com.daema.core.wms.repository.custom;


import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.dto.request.MoveMgmtRequestDto;
import com.daema.core.wms.dto.request.MoveStockRequestDto;
import com.daema.core.wms.dto.response.MoveMgmtResponseDto;
import com.daema.core.wms.dto.response.MoveStockResponseDto;
import com.daema.core.wms.dto.response.TransResponseDto;
import com.daema.core.wms.domain.enums.WmsEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomMoveStockRepository {
    Page<MoveStockResponseDto> getMoveTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto moveStockRequestDto);

    Page<TransResponseDto> getTransTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto moveStockRequestDto);

    Page<TransResponseDto> getFaultyTransTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto moveStockRequestDto);

    List<Store> getTransStoreList(long storeId,boolean isAdmin);


    Page<MoveMgmtResponseDto> getMoveMgmtList(MoveMgmtRequestDto requestDto);
}
