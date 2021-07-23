package com.daema.core.scm.dto;


import com.daema.core.scm.domain.enums.ScmEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationBasicDto {

    @ApiModelProperty(value = "신청서 아이디", example = "0")
    private Long applId;

    private ScmEnum.TaskState.LogisState logisState;

    private ScmEnum.TaskState.ConsultState consultState;

    private ScmEnum.TaskState.OpeningState openingState;

    private Long saleStoreId;

    private Long openingStoreId;

    private Long parentSaleStoreId;

    private String priorityTargetYn;

    private Long membersId;
}
