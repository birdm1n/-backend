package com.daema.core.scm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskBoardDto {

    @ApiModelProperty(value = "업무수정 보드 아이디")
    private Long taskBoardId;

    private String checkYN;

    private List<TaskMemoDto> taskMemoDtoList;
}

