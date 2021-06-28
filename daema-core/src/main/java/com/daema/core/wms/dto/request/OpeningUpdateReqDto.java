package com.daema.core.wms.dto.request;

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
public class OpeningUpdateReqDto {

    @ApiModelProperty(value = "개통 ID", example = "0",required = true)
    private Long openingId;

    @ApiModelProperty(value = "철회 일자", required = true)
    private LocalDate cancelDate;

    @ApiModelProperty(value = "철회 메모")
    private String cancelMemo;

}