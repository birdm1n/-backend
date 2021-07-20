package com.daema.core.scm.dto;


import com.daema.core.scm.domain.enums.ScmEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicInfoDto {


    private Long appFormId;

    private ScmEnum.TaskState.LogisState logisState;

    private ScmEnum.TaskState.ConsultState consultState;

    private ScmEnum.TaskState.OpeningState openingState;

    private Long saleStoreId;

    private Long openingStoreId;

    private Long parentSaleStoreId;

    private String priorityTargetYn;

    private Long membersId;
}
