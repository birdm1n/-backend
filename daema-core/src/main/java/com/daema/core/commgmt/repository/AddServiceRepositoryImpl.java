package com.daema.core.commgmt.repository;

import com.daema.core.base.domain.QCodeDetail;
import com.daema.core.base.domain.common.RetrieveClauseBuilder;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.commgmt.domain.AddService;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import com.daema.core.commgmt.repository.custom.CustomAddServiceRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.daema.core.commgmt.domain.QAddService.addService;

public class AddServiceRepositoryImpl extends QuerydslRepositorySupport implements CustomAddServiceRepository {

    public AddServiceRepositoryImpl() {
        super(AddService.class);
    }

    @Override
    public Page<AddService> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin) {

        JPQLQuery<AddService> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(addService.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()));

        if(!isAdmin){
            builder.and(addService.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg()));
        }

        QCodeDetail telecom = new QCodeDetail("telecom");

        query.select(Projections.fields(
                AddService.class
                ,addService.addSvcId
                ,addService.addSvcName
                ,addService.addSvcCharge
                ,addService.addSvcType
                ,addService.telecom
                ,addService.originKey
                ,addService.addSvcMemo
                ,addService.useYn
                ,addService.delYn
                ,addService.regiDateTime
                ,telecom.codeNm.as("telecomName")
            )
        );

        query.from(addService)
                .innerJoin(telecom)
                .on(addService.telecom.eq(telecom.codeSeq)
                        .and(telecom.codeId.eq("TELECOM"))
                        .and(telecom.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
                )
                .where(
                        builder
                        ,containsAddSvcName(requestDto.getAddSvcName())
                        ,eqTelecom(requestDto.getTelecom())
                        ,eqUseYn(requestDto.getUseYn())
                        ,betweenStartDateEndDate(requestDto.getSrhStartDate(), requestDto.getSrhEndDate())
                        ,eqAddSvcType(requestDto.getAddSvcType())
                )
                .orderBy(addService.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<AddService> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression containsAddSvcName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return addService.addSvcName.contains(name);
    }

    private BooleanExpression eqTelecom(Integer[] name) {
        if (name == null
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)
                || (name != null && name.length <= 0)) {
            return null;
        }
        return addService.telecom.in(name);
    }

    private BooleanExpression eqUseYn(String name) {
        if (StringUtils.isEmpty(name) || "all".equals(name)) {
            return null;
        }
        return addService.useYn.eq(name);
    }

    private BooleanExpression betweenStartDateEndDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)
                || StringUtils.isEmpty(endDate)) {
            return null;
        }
        return addService.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate, "s")
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate, "e"));
    }

    private BooleanExpression eqAddSvcType(TypeEnum.AddSvcType addSvcType) {
        if (addSvcType == null) {
            return null;
        }

        return addService.addSvcType.eq(addSvcType);
    }
}
