package com.daema.rest.wms.dto;

import com.daema.base.domain.Member;
import com.daema.wms.domain.InStock;
import com.daema.wms.domain.enums.WmsEnum;
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
    private Member regiUserId;
    private LocalDateTime updDateTime;
    private Member updUserId;
    private String delYn;
    private Long provId;
    private Long stockId;
    private Long dvcId;


    public static InStockMgmtDto from(InStock inStock) {
        return InStockMgmtDto.builder()
                .inStockId(inStock.getInStockId())
                .inStockStatus(inStock.getInStockStatus())
                .inStockAmt(inStock.getInStockAmt())
                .inStockMemo(inStock.getInStockMemo())
                .regiDateTime(inStock.getRegiDateTime())
                .regiUserId(inStock.getRegiUserId())
                .updDateTime(inStock.getUpdDateTime())
                .updUserId(inStock.getUpdUserId())
                .delYn(inStock.getDelYn())
                .provId(inStock.getProvider().getProvId())
                .dvcId(inStock.getDevice().getDvcId())
                .build();
    }

}