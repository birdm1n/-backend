package com.daema.wms.repository;

import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.base.enums.StatusEnum;
import com.daema.wms.domain.Provider;
import com.daema.wms.domain.dto.request.ProviderRequestDto;
import com.daema.wms.repository.custom.CustomProviderRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.daema.base.domain.QMembers.members;
import static com.daema.wms.domain.QProvider.provider;


public class ProviderRepositoryImpl extends QuerydslRepositorySupport implements CustomProviderRepository {

    public ProviderRepositoryImpl() {
        super(Provider.class);
    }

    @Override
    public Page<Provider> getSearchPage(ProviderRequestDto requestDto) {

        JPQLQuery<Provider> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                Provider.class
                , provider.provId
                , provider.provName
                , provider.chargerPhone
                , provider.chargerPhone1
                , provider.chargerPhone2
                , provider.chargerPhone3
                , provider.chargerName
                , provider.chargerEmail
                , provider.returnZipCode
                , provider.returnAddr
                , provider.returnAddrDetail
                , provider.regiDateTime
                , provider.updDateTime
                , provider.useYn
                , members.name.as("name")
        ));

        query.from(provider);

        query.innerJoin(members)
                .on(provider.updUserId.eq(members.seq)
                );

        query.where(
                provider.storeId.eq(requestDto.getStoreId())
                ,provider.delYn.eq(StatusEnum.FLAG_N.getStatusMsg())
                ,eqProvId(requestDto.getProvId())
                ,containsChargerName(requestDto.getChargerName())
                ,containsChargerPhone(requestDto.getChargerPhone())
                ,containsChargerEmail(requestDto.getChargerEmail())
                ,containsReturnAddr(requestDto.getReturnAddr())
                ,eqUseYn(requestDto.getUseYn())
                ,betweenStartDateEndDate(requestDto.getSrhStartDate(), requestDto.getSrhEndDate())
        ).orderBy(provider.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<Provider> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression eqProvId(Long provId) {
        if (provId == null) {
            return null;
        }
        return provider.provId.eq(provId);
    }

    private BooleanExpression containsChargerName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return provider.chargerName.contains(name);
    }

    private BooleanExpression containsChargerPhone(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return provider.chargerPhone.contains(name);
    }

    private BooleanExpression containsChargerEmail(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return provider.chargerEmail.contains(name);
    }

    private BooleanExpression containsReturnAddr(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return provider.returnAddr.contains(name).or(provider.returnAddrDetail.contains(name));
    }

    private BooleanExpression eqUseYn(String name) {
        if (StringUtils.isEmpty(name) || "all".equals(name)) {
            return null;
        }
        return provider.useYn.eq(name);
    }

    private BooleanExpression betweenStartDateEndDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)
                || StringUtils.isEmpty(endDate)) {
            return null;
        }
        return provider.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate, "s")
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate, "e"));
    }
}
