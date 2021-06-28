package com.daema.core.wms.dto.response;

import com.daema.core.wms.domain.InStockWait;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InStockWaitGroupDto {

    private String telecomName;
    private String stockName;
    private String makerName;
    private String goodsName;
    private String modelName;
    private String capacity;
    private String colorName;
    private long totalCount;

    public static InStockWaitGroupDto from (InStockWait entity) {
        return InStockWaitGroupDto.builder()
               .telecomName(entity.getTelecomName())
               .stockName(entity.getStockName())
               .makerName(entity.getMakerName())
               .goodsName(entity.getGoodsName())
               .modelName(entity.getModelName())
               .capacity(entity.getCapacity())
               .colorName(entity.getColorName())
                .build();
    }

}
