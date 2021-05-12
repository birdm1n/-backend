package com.daema.wms.repository.custom;


import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import org.springframework.data.domain.Page;

public interface CustomMoveStockRepository {
    Page<MoveStockResponseDto> getMoveTypeList(WmsEnum.MovePathType movePathType);

    Page<MoveStockResponseDto> getTransTypeList(WmsEnum.MovePathType movePathType);
}
