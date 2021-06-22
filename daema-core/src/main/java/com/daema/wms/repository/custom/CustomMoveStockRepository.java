package com.daema.wms.repository.custom;


import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.dto.request.MoveMgmtRequestDto;
import com.daema.wms.domain.dto.request.MoveStockRequestDto;
import com.daema.wms.domain.dto.response.MoveMgmtResponseDto;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.dto.response.TransResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomMoveStockRepository {
    Page<MoveStockResponseDto> getMoveTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto moveStockRequestDto);

    Page<TransResponseDto> getTransTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto moveStockRequestDto);

    Page<TransResponseDto> getFaultyTransTypeList(WmsEnum.MovePathType movePathType, MoveStockRequestDto moveStockRequestDto);

    List<Store> getTransStoreList(long storeId,boolean isAdmin);


    Page<MoveMgmtResponseDto> getMoveMgmtList(MoveMgmtRequestDto requestDto);
}
