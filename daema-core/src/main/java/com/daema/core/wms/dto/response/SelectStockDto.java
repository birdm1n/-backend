package com.daema.core.wms.dto.response;

import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectStockDto {

    private Long stockId;
    private String stockName;
    private String stockType;
    private Long storeId;
    private WmsEnum.StockStatStr statusStr;

}
