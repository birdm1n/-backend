package com.daema.wms.repository;

import com.daema.wms.domain.StoreStockCheck;
import com.daema.wms.domain.dto.response.StoreStockCheckListDto;
import com.daema.wms.repository.custom.CustomStoreStockCheckRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.daema.base.domain.QMember.member;
import static com.daema.commgmt.domain.QOrganization.organization;
import static com.daema.wms.domain.QStoreStockCheck.storeStockCheck;

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

                , member.name.as("name")

                , organization.orgName.as("orgName")
                )
        )

                .from(storeStockCheck)
                .innerJoin(storeStockCheck.regiUserId, member).on(
                        storeStockCheck.storeStock.storeStockId.eq(storeStockId)
        )
                .innerJoin(organization).on(
                        member.orgId.eq(organization.orgId)
        )

                .orderBy(storeStockCheck.regiDateTime.desc());

        List<StoreStockCheckListDto> resultList = query.fetch();

        return resultList;
    }
}