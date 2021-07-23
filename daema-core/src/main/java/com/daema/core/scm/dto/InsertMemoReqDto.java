package com.daema.core.scm.dto;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.domain.taskboard.ApplicationTaskBoard;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsertMemoReqDto {
    @ApiModelProperty(value = "신청서 아이디", example = "0", required = true)
    private Long applId;
    @ApiModelProperty(value = "카테고리", required = true)
    private ScmEnum.MemoCategory category;
    @ApiModelProperty(value = "메모 내용", required = true)
    private String memoContents;

    @ApiModelProperty(hidden = true)
    private ApplicationTaskBoard taskBoard;
    @ApiModelProperty(hidden = true)
    private Store store;/*
    @ApiModelProperty(hidden = true)
    private Organization organization;*/
}
