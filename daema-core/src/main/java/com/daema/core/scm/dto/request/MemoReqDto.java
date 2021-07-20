package com.daema.core.scm.dto.request;

import com.daema.core.scm.domain.enums.ScmEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoReqDto {

    private Long appFormId;

    @ApiModelProperty(value = "업무수정 코멘트 아이디")
    private Long taskMemoId;

    @ApiModelProperty(value = "상담 상태",  required = true)
    private ScmEnum.TaskState.ConsultState consultState;

    @ApiModelProperty(value = "물류 상태",  required = true)
    private ScmEnum.TaskState.LogisState logisState;

    @ApiModelProperty(value = "개통 상태",  required = true)
    private ScmEnum.TaskState.OpeningState openingState;

    @ApiModelProperty(value = "사유")
    private String reason;

}
