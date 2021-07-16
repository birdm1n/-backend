package com.daema.core.scm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskUpdateBoardDto {

    @ApiModelProperty(value = "업무수정 보드 아이디")
    private Long taskUpdateBoardId;

    @ApiModelProperty(value = "전체 코멘트 아이디")
    private List<Long> taskUpdateCommentIds;
}

