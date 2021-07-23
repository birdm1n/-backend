package com.daema.core.scm.domain;

import com.daema.core.base.domain.Members;
import com.daema.core.base.domain.common.BaseUserInfoEntity;
import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.scm.domain.application.Application;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.ApplicationBasicDto;
import lombok.*;

import javax.persistence.*;


@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applId")
@ToString(exclude = {"application"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_basic", comment = "기본 정보")
public class ApplicationBasic extends BaseUserInfoEntity {

    @Id
    private Long applId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appl_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 아이디'")
    @MapsId
    private Application application;

    @Column(columnDefinition = "varchar(255) comment '개통 업무 상태'")
    private ScmEnum.TaskState.LogisState logisState;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) comment '상담 업무 상태'")
    private ScmEnum.TaskState.ConsultState consultState;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) comment '물류 업무 상태'")
    private ScmEnum.TaskState.OpeningState openingState;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_store_id", columnDefinition = "BIGINT unsigned comment '영업 관리점 아이디'")
    private Store saleStore;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_store_id", columnDefinition = "BIGINT unsigned comment '개통 관리점 아이디'")
    private OpenStore openingStore;

    @Column(name = "parent_sale_store_id", columnDefinition = "BIGINT unsigned comment '상위 영업 관리점 아이디'")
    private Long parentSaleStoreId;

    @Column(name = "priority_target_yn", columnDefinition = "char(1) comment '우선 순위 대상'")
    private String priorityTargetYn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members;


    public static ApplicationBasic create(Application application,ApplicationBasicDto applicationBasicDto){
       return ApplicationBasic.builder()
               .application(application)
                .consultState(applicationBasicDto.getConsultState())
               .logisState(applicationBasicDto.getLogisState())
               .openingState(applicationBasicDto.getOpeningState())
               .members(Members.builder()
                       .seq(applicationBasicDto.getMembersId())
                       .build())
               .saleStore(Store.builder()
                       .storeId(applicationBasicDto.getParentSaleStoreId())
                       .build())
               .openingStore(OpenStore.builder()
                       .openStoreId(applicationBasicDto.getOpeningStoreId()).build())
               .parentSaleStoreId(applicationBasicDto.getParentSaleStoreId())
               .priorityTargetYn(applicationBasicDto.getPriorityTargetYn())
               .build();

    }

    public static void update(ApplicationBasic applicationBasic, ApplicationBasicDto applicationBasicDto) {


        ScmEnum.TaskState.ConsultState consultState = applicationBasic.getConsultState();
        ScmEnum.TaskState.LogisState logisState = applicationBasic.getLogisState();
        ScmEnum.TaskState.OpeningState openingState = applicationBasic.getOpeningState();

        if(consultState != null)
            applicationBasic.setConsultState(consultState);
        if(logisState != null)
            applicationBasic.setLogisState(logisState);
        if(openingState != null)
            applicationBasic.setOpeningState(openingState);
        applicationBasic.setOpeningStore(OpenStore.builder()
                .storeId(applicationBasicDto.getOpeningStoreId()).build());
        applicationBasic.setSaleStore(Store.builder()
                .storeId(applicationBasicDto.getSaleStoreId()).build());
        applicationBasic.setParentSaleStoreId(applicationBasic.getParentSaleStoreId());
        applicationBasic.setPriorityTargetYn(applicationBasic.getPriorityTargetYn());
        applicationBasic.setMembers(Members.builder()
                .seq(applicationBasicDto.getMembersId())
                .build());


    }

    public static ApplicationBasicDto From(ApplicationBasic applicationBasic){
        return ApplicationBasicDto.builder()
                .consultState(applicationBasic.getConsultState())
                .logisState(applicationBasic.getLogisState())
                .openingState(applicationBasic.getOpeningState())
                .membersId(applicationBasic.getMembers().getSeq())
                .saleStoreId(applicationBasic.getSaleStore().getStoreId())
                .openingStoreId(applicationBasic.getOpeningStore().getStoreId())
                .parentSaleStoreId(applicationBasic.getParentSaleStoreId())
                .priorityTargetYn(applicationBasic.getPriorityTargetYn())
                .build();
    }
}
