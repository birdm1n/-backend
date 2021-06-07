package com.daema.rest.wms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningInsertReqDto {

    @ApiModelProperty(value = "기기 ID", example = "0",required = true)
    private Long dvcId;

    @ApiModelProperty(value = "개통일자", required = true)
    private LocalDate openingDate;

    @ApiModelProperty(value = "개통 메모")
    private String openingMemo;

}