package com.daema.wms.repository;

import com.daema.commgmt.domain.QStore;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.MoveStock;
import com.daema.wms.domain.dto.response.MoveStockResponseDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.custom.CustomMoveStockRepository;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.daema.commgmt.domain.QStore.store;

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

    @Override
    public List<Store> getTransStoreList(long storeId) {
        JPQLQuery<Store> query = getQuerydsl().createQuery();
        return query.select(store)
                .from(store)
                .where(
                        store.useYn.eq("Y"),
                        store.storeId.ne(storeId)
                )
                .fetch();
    }
}
