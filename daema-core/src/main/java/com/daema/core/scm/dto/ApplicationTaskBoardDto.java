package com.daema.core.scm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationTaskBoardDto {

    @ApiModelProperty(value = "신청서 아이디", example = "0")
    private Long applId;

    private String checkYN;

    private List<ApplicationTaskMemoDto> applicationTaskMemoDtoList;
}

