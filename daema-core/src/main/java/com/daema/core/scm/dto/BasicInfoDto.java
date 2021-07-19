package com.daema.core.scm.dto;


import com.daema.core.base.domain.Members;
import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.domain.taskupdateboard.TaskUpdateBoard;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

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

    private Long taskUpdateBoardId;
}
