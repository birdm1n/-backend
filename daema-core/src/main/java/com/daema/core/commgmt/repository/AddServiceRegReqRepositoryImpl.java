package com.daema.core.commgmt.repository;

import com.daema.core.base.domain.QCodeDetail;
import com.daema.core.base.domain.common.RetrieveClauseBuilder;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.commgmt.domain.AddServiceRegReq;
import com.daema.core.commgmt.domain.AddServiceRegReqReject;
import com.daema.core.commgmt.domain.QStore;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import com.daema.core.commgmt.repository.custom.CustomAddServiceRegReqRepository;
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

import static com.daema.core.commgmt.domain.QAddServiceRegReq.addServiceRegReq;
import static com.daema.core.commgmt.domain.QAddServiceRegReqReject.addServiceRegReqReject;

public class AddServiceRegReqRepositoryImpl extends QuerydslRepositorySupport implements CustomAddServiceRegReqRepository {

    public AddServiceRegReqRepositoryImpl() {
        super(AddServiceRegReq.class);
    }

    @Override
    public Page<AddServiceRegReq> getSearchPage(ComMgmtRequestDto requestDto, boolean isAdmin) {

        JPQLQuery<AddServiceRegReq> query = getQuerydsl().createQuery();

        BooleanBuilder builder = new BooleanBuilder();

        if(!isAdmin){
            builder.and(addServiceRegReq.reqStoreId.eq(requestDto.getStoreId()));
        }

        QCodeDetail telecom = new QCodeDetail("telecom");
        QStore store = QStore.store;

        query.select(Projections.fields(
                AddServiceRegReq.class
                ,addServiceRegReq.addSvcRegReqId
                ,addServiceRegReq.addSvcName
                ,addServiceRegReq.addSvcCharge
                ,addServiceRegReq.addSvcMemo
                ,addServiceRegReq.addSvcType
                ,addServiceRegReq.telecom
                ,addServiceRegReq.reqStatus
                ,addServiceRegReq.reqStoreId
                ,addServiceRegReq.regiDateTime
                ,telecom.codeNm.as("telecomName")
                ,store.storeName.as("reqStoreName")
                ,Projections.fields(AddServiceRegReqReject.class
                        ,addServiceRegReqReject.addSvcRegReqId
                        ,addServiceRegReqReject.rejectComment
                        ,addServiceRegReqReject.rejectDateTime
                        ,addServiceRegReqReject.rejectUserId
                ).as("addServiceRegReqReject")
            )
        );

        query.from(addServiceRegReq)
                .innerJoin(telecom)
                .on(addServiceRegReq.telecom.eq(telecom.codeSeq)
                        .and(telecom.codeId.eq("TELECOM"))
                        .and(telecom.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
                )
                .innerJoin(store)
                .on(addServiceRegReq.reqStoreId.eq(store.storeId))
                .leftJoin(addServiceRegReqReject)
                .on(addServiceRegReq.addSvcRegReqId.eq(addServiceRegReqReject.addSvcRegReqId))
                .where(
                        builder
                        ,containsAddSvcName(requestDto.getAddSvcName())
                        ,eqTelecom(requestDto.getTelecom())
                        ,eqStatus(requestDto.getReqStatus())
                        ,betweenStartDateEndDate(requestDto.getSrhStartDate(), requestDto.getSrhEndDate())
                        ,eqAddSvcType(requestDto.getAddSvcType())
                )
                .orderBy(addServiceRegReq.regiDateTime.desc());

        PageRequest pageable = RetrieveClauseBuilder.setOffsetLimit(query, requestDto);

        List<AddServiceRegReq> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }

    private BooleanExpression containsAddSvcName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return addServiceRegReq.addSvcName.contains(name);
    }

    private BooleanExpression eqTelecom(Integer[] name) {
        if (name == null
                || Arrays.stream(name).anyMatch(telecom -> telecom == 0)
                || (name != null && name.length <= 0)) {
            return null;
        }
        return addServiceRegReq.telecom.in(name);
    }

    private BooleanExpression eqStatus(Integer name) {
        if (name == null || name == 0) {
            return null;
        }
        return addServiceRegReq.reqStatus.eq(name.toString());
    }

    private BooleanExpression betweenStartDateEndDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)
                || StringUtils.isEmpty(endDate)) {
            return null;
        }
        return addServiceRegReq.regiDateTime.between(RetrieveClauseBuilder.stringToLocalDateTime(startDate, "s")
                , RetrieveClauseBuilder.stringToLocalDateTime(endDate, "e"));
    }

    private BooleanExpression eqAddSvcType(TypeEnum.AddSvcType addSvcType) {
        if (addSvcType == null) {
            return null;
        }

        return addServiceRegReq.addSvcType.eq(addSvcType);
    }

}
