package com.daema.core.scm.dto;

import com.daema.core.scm.domain.enums.ScmEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationTaskMemoDto
{
    @ApiModelProperty(value = "신청서 업무 수정 코멘트 아이디")
    private Long applTaskMemoId;

    @ApiModelProperty(value = "상담 상태",  required = true)
    private ScmEnum.TaskState.ConsultState consultState;

    @ApiModelProperty(value = "물류 상태",  required = true)
    private ScmEnum.TaskState.LogisState logisState;

    @ApiModelProperty(value = "개통 상태",  required = true)
    private ScmEnum.TaskState.OpeningState openingState;

    @ApiModelProperty(value = "사유")
    private String reason;

    @ApiModelProperty(value = "신청서 업무 수정 보드 아이디")
    private Long applTaskBoardId;

}
