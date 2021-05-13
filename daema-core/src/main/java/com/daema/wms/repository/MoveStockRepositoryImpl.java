package com.daema.wms.repository;

import com.daema.wms.domain.MoveStock;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomMoveStockRepository;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MoveStockRepositoryImpl extends QuerydslRepositorySupport implements CustomMoveStockRepository {
    public MoveStockRepositoryImpl() {
        super(MoveStock.class);
    }

    @Override
    public Page<MoveStockResponseDto> getMoveTypeList(WmsEnum.MovePathType movePathType) {
        JPQLQuery<MoveStockResponseDto> query = getQuerydsl().createQuery();



        return null;
    }

    @Override
    public Page<MoveStockResponseDto> getTransTypeList(WmsEnum.MovePathType movePathType) {
        JPQLQuery<MoveStockResponseDto> query = getQuerydsl().createQuery();

        return null;
    }
}
