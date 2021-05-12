package com.daema.wms.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockListDto {

    private int depth;

    private long stockId;

    private long storeId;

    private long parentStockId;

    private String stockName;

    private String stockType;

    private String chargerName;

    private String chargerPhone;

    private String hierarchy;

    private int dvcCnt;
}
