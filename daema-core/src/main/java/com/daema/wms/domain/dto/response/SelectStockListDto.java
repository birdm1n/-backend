package com.daema.wms.domain.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectStockListDto {

    private Long stockId;
    private String stockName;
    private String stockType;

}
