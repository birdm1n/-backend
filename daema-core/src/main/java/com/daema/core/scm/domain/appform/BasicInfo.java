package com.daema.core.scm.domain;

import com.daema.core.base.domain.Members;
import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.scm.domain.customer.CourtProctor;
import com.daema.core.scm.domain.customer.Customer;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.BasicInfoDto;
import com.daema.core.scm.dto.CustomerDto;
import lombok.*;

import javax.persistence.*;


@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "basicInfoId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "basic_info", comment = "기본 정보")
public class BasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basic_info_id", columnDefinition = "BIGINT UNSIGNED comment '기본 정보'")
    private Long basicInfoId;

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


    public static BasicInfo create(BasicInfoDto basicInfoDto){
       return BasicInfo.builder()
                .consultState(basicInfoDto.getConsultState())
               .logisState(basicInfoDto.getLogisState())
               .openingState(basicInfoDto.getOpeningState())
               .members(Members.builder()
                       .seq(basicInfoDto.getMembersId())
                       .build())
               .saleStore(Store.builder()
                       .storeId(basicInfoDto.getParentSaleStoreId())
                       .build())
               .openingStore(OpenStore.builder()
                       .openStoreId(basicInfoDto.getOpeningStoreId()).build())
               .parentSaleStoreId(basicInfoDto.getParentSaleStoreId())
               .priorityTargetYn(basicInfoDto.getPriorityTargetYn())
               .build();

    }

    public static void update(BasicInfo basicInfo, BasicInfoDto basicInfoDto) {


        ScmEnum.TaskState.ConsultState consultState = basicInfo.getConsultState();
        ScmEnum.TaskState.LogisState logisState = basicInfo.getLogisState();
        ScmEnum.TaskState.OpeningState openingState = basicInfo.getOpeningState();

        if(consultState != null)
            basicInfo.setConsultState(consultState);
        if(logisState != null)
            basicInfo.setLogisState(logisState);
        if(openingState != null)
            basicInfo.setOpeningState(openingState);
        basicInfo.setOpeningStore(OpenStore.builder()
                .storeId(basicInfoDto.getOpeningStoreId()).build());
        basicInfo.setSaleStore(Store.builder()
                .storeId(basicInfoDto.getSaleStoreId()).build());
        basicInfo.setParentSaleStoreId(basicInfo.getParentSaleStoreId());
        basicInfo.setPriorityTargetYn(basicInfo.getPriorityTargetYn());
        basicInfo.setMembers(Members.builder()
                .seq(basicInfoDto.getMembersId())
                .build());


    }

    public static BasicInfoDto From(BasicInfo basicInfo){
        return BasicInfoDto.builder()
                .consultState(basicInfo.getConsultState())
                .logisState(basicInfo.getLogisState())
                .openingState(basicInfo.getOpeningState())
                .membersId(basicInfo.getMembers().getSeq())
                .saleStoreId(basicInfo.getSaleStore().getStoreId())
                .openingStoreId(basicInfo.getOpeningStore().getStoreId())
                .parentSaleStoreId(basicInfo.getParentSaleStoreId())
                .priorityTargetYn(basicInfo.getPriorityTargetYn())
                .build();
    }
}
