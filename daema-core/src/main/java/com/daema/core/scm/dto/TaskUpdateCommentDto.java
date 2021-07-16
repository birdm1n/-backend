package com.daema.core.scm.dto;

import com.daema.core.scm.domain.enums.ScmEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskUpdateCommentDto
{
    @ApiModelProperty(value = "업무수정 아이디")
    private Long taskUpdateId;

    @ApiModelProperty(value = "업무 상태",  required = true)
    private ScmEnum.TaskState taskState;

    @ApiModelProperty(value = "사유")
    private String reason;

    @ApiModelProperty(value = "업무수정 보드 아이디")
    private Long taskUpdateBoardId;

}
