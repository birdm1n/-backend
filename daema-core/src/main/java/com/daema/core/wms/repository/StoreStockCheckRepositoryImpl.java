package com.daema.core.wms.repository;

import com.daema.core.wms.domain.StoreStockCheck;
import com.daema.core.wms.dto.response.StoreStockCheckListDto;
import com.daema.core.wms.repository.custom.CustomStoreStockCheckRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.daema.core.base.domain.QMembers.members;
import static com.daema.core.commgmt.domain.QOrganization.organization;
import static com.daema.core.wms.domain.QStoreStockCheck.storeStockCheck;

public class StoreStockCheckRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreStockCheckRepository {

    public StoreStockCheckRepositoryImpl() {
        super(StoreStockCheck.class);
    }

    @Override
    public List<StoreStockCheckListDto> getStoreStockCheckHistory(Long storeStockId) {

        JPQLQuery<StoreStockCheckListDto> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                StoreStockCheckListDto.class
                , storeStockCheck.regiDateTime.as("regiDateTime")

                , members.name.as("name")

                , organization.orgName.as("orgName")
                )
        )

                .from(storeStockCheck)
                .innerJoin(storeStockCheck.regiUserId, members).on(
                        storeStockCheck.storeStock.storeStockId.eq(storeStockId)
        )
                .innerJoin(organization).on(
                members.orgId.eq(organization.orgId)
        )

                .orderBy(storeStockCheck.regiDateTime.desc());

        List<StoreStockCheckListDto> resultList = query.fetch();

        return resultList;
    }
}