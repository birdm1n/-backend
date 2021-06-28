package com.daema.core.wms.dto;

import com.daema.core.base.domain.Members;
import com.daema.core.wms.domain.InStock;
import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InStockMgmtDto {

    private Long inStockId;
    private WmsEnum.InStockStatus inStockStatus;
    private int inStockAmt;
    private String inStockMemo;
    private LocalDateTime regiDateTime;
    private Members regiUserId;
    private LocalDateTime updDateTime;
    private Members updUserId;
    private String delYn;
    private Long provId;
    private Long stockId;
    private Long dvcId;


    public static InStockMgmtDto from(InStock inStock) {
        return InStockMgmtDto.builder()
                .inStockId(inStock.getInStockId())
                .inStockMemo(inStock.getInStockMemo())
                .regiDateTime(inStock.getRegiDateTime())
                .regiUserId(inStock.getRegiUserId())
                .updDateTime(inStock.getUpdDateTime())
                .updUserId(inStock.getUpdUserId())
                .delYn(inStock.getDelYn())
                .provId(inStock.getProvider().getProvId())
                .stockId(inStock.getStock().getStockId())
                .dvcId(inStock.getDevice().getDvcId())
                .build();
    }

}